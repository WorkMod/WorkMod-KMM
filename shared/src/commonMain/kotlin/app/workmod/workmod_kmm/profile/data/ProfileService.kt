package app.workmod.workmod_kmm.profile.data

import app.workmod.workmod_kmm.common.ApiResponse
import app.workmod.workmod_kmm.common.Prefs
import app.workmod.workmod_kmm.profile.data.response.AddProfileResponse
import app.workmod.workmod_kmm.profile.data.response.DeleteProfileResponse
import app.workmod.workmod_kmm.profile.data.response.GetAllProfilesResponse
import app.workmod.workmod_kmm.profile.data.response.GetProfileResponse
import app.workmod.workmod_kmm.profile.domain.model.Education
import app.workmod.workmod_kmm.profile.domain.model.Profile
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

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

            setBody(
                mapOf(
                    "title" to title,
                    "name" to name,
                    "designation" to designation,
                    "email" to email,
                    "educations" to educations.toTypedArray(),
                    "phone" to phone,
                    "address" to address,
                    "nationality" to nationality,
                    "description" to description
                )
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
                mapOf(
                    "profileId" to profileId,
                    "title" to title,
                    "name" to name,
                    "designation" to designation,
                    "email" to email,
                    "phone" to phone,
                    "address" to address,
                    "nationality" to nationality,
                    "description" to description
                )
            )
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

}