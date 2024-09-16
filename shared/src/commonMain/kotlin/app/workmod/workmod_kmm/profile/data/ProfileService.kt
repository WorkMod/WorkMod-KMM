package app.workmod.workmod_kmm.profile.data

import app.workmod.workmod_kmm.common.ApiResponse
import app.workmod.workmod_kmm.common.Constants.DOWNLOAD_BUFFER_SIZE
import app.workmod.workmod_kmm.common.Prefs
import app.workmod.workmod_kmm.profile.data.model.AddProfileModel
import app.workmod.workmod_kmm.profile.data.response.AddProfileResponse
import app.workmod.workmod_kmm.profile.data.response.DeleteProfileResponse
import app.workmod.workmod_kmm.profile.data.response.DownloadProfileResponse
import app.workmod.workmod_kmm.profile.data.response.GetAllProfilesResponse
import app.workmod.workmod_kmm.profile.data.response.GetProfileResponse
import app.workmod.workmod_kmm.profile.domain.model.Education
import app.workmod.workmod_kmm.profile.domain.model.Employment
import app.workmod.workmod_kmm.profile.domain.model.Profile
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.prepareGet
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentDisposition.Companion.File
import io.ktor.http.ContentType
import io.ktor.http.contentLength
import io.ktor.http.contentType
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.core.isEmpty
import io.ktor.utils.io.core.readBytes

class ProfileService constructor(
    private val client: HttpClient,
    private val prefs: Prefs
) {

    private val _profilesUrl = "http://192.168.1.229:5000/api/profile/"
    //private val _profilesUrl = "${Constants.BASE_URL}/api/profile/"

    private val _getUrl = _profilesUrl
    private val _getAllUrl = "${_profilesUrl}all"
    private val _addUrl = _profilesUrl
    private val _updateUrl = _profilesUrl
    private val _deleteUrl = _profilesUrl
    private val _downloadProfileUrl = "${_profilesUrl}download-profile/"


    suspend fun getProfile(profileId: String): ApiResponse<Profile> {
        val token = prefs.getToken()
        val response = client.get("${_getUrl}$profileId") {
            contentType(ContentType.Application.Json)
            headers { append("Authorization", "Bearer $token") }
            parameter("profileId", profileId)
        }

        val getProfileResponse: GetProfileResponse = response.body()
        val apiResponse = ApiResponse(
            response.status.value,
            getProfileResponse.profile?.toProfile(),
            getProfileResponse.message
        )

        return apiResponse
    }

    suspend fun getAllProfiles(): ApiResponse<List<Profile>> {
        val token = prefs.getToken()
        val response = client.get(_getAllUrl) {
            contentType(ContentType.Application.Json)
            headers { append("Authorization", "Bearer $token") }
        }

        val getAllProfileResponse: GetAllProfilesResponse = response.body()
        val apiResponse = ApiResponse(
            response.status.value,
            getAllProfileResponse.profiles.map { it.toProfile() },
            getAllProfileResponse.message
        )

        return apiResponse
    }

    suspend fun addProfile(
        title: String,
        name: String,
        designation: String,
        email: String,
        employments: List<Employment>,
        educations: List<Education>,
        phone: String,
        address: String,
        nationality: String,
        description: String
    ): AddProfileResponse {

        val token = prefs.getToken()

        val response = client.post(_addUrl) {
            contentType(ContentType.Application.Json)
            headers { append("Authorization", "Bearer $token") }

            setBody(AddProfileModel(
                title = title,
                name = name,
                designation = designation,
                email = email,
                employments = employments,
                educations = educations,
                phone = phone,
                address = address,
                nationality = nationality,
                description = description)
            )
        }

        val addProfileResponse: AddProfileResponse = response.body()
        addProfileResponse.statusCode = response.status.value

        return addProfileResponse
    }

    suspend fun updateProfile(
        profileId: String,
        title: String,
        name: String,
        designation: String,
        email: String,
        employments: List<Employment>,
        educations: List<Education>,
        phone: String,
        address: String,
        nationality: String,
        description: String
    ): AddProfileResponse {

        val token = prefs.getToken()

        val response = client.put(_getUrl) {
            contentType(ContentType.Application.Json)
            headers { append("Authorization", "Bearer $token") }
            parameter("profileId", profileId)
            setBody(
                AddProfileModel(
                    profileId = profileId,
                    title = title,
                    name = name,
                    designation = designation,
                    email = email,
                    employments = employments,
                    educations = educations,
                    phone = phone,
                    address = address,
                    nationality = nationality,
                    description = description
                )
            )
            /*setBody(
                mapOf(
                    "profileId" to profileId,
                    "title" to title,
                    "name" to name,
                    "designation" to designation,
                    "email" to email,
                    "employments" to employments,
                    "educations" to educations,
                    "phone" to phone,
                    "address" to address,
                    "nationality" to nationality,
                    "description" to description
                )
            )*/
        }

        val addProfileResponse: AddProfileResponse = response.body()
        addProfileResponse.statusCode = response.status.value

        return addProfileResponse
    }

    suspend fun deleteProfile(profileId: String): DeleteProfileResponse {
        val token = prefs.getToken()

        val response = client.delete("${_deleteUrl}$profileId") {
            contentType(ContentType.Application.Json)
            headers { append("Authorization", "Bearer $token") }
            parameter("profileId", profileId)
        }

        val deleteProfileResponse: DeleteProfileResponse = response.body()
        deleteProfileResponse.statusCode = response.status.value

        return deleteProfileResponse
    }

    suspend fun downloadProfile(profileId: String): ApiResponse<DownloadProfileResponse> {
        val token = prefs.getToken()
        val response = client.get("${_downloadProfileUrl}$profileId") {
            contentType(ContentType.Application.Json)
            headers { append("Authorization", "Bearer $token") }
            parameter("profileId", profileId)
        }

        //https://github.com/ktorio/ktor-documentation/blob/2.3.3/codeSnippets/snippets/client-download-streaming/src/main/kotlin/com/example/Application.kt

        //create common Write file method for android and iOS
        //val file = File.createTempFile("files", "index")
        client.prepareGet("https://ktor.io/").execute { httpResponse ->
            val channel: ByteReadChannel = httpResponse.body()
            while (!channel.isClosedForRead) {
                val packet = channel.readRemaining(DOWNLOAD_BUFFER_SIZE.toLong())
                while (!packet.isEmpty) {
                    val bytes = packet.readBytes()
                    file.appendBytes(bytes)
                    println("Received ${file.length()} bytes from ${httpResponse.contentLength()}")
                }
            }
            println("A file saved to ${file.path}")
        }
        val downloadProfileResponse: DownloadProfileResponse = response.body()
        val apiResponse = ApiResponse(
            response.status.value,
            getProfileResponse.profile?.toProfile(),
            getProfileResponse.message
        )

        return apiResponse
    }

}