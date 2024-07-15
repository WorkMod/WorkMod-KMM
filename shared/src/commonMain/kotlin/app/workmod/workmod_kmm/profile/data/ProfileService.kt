package app.workmod.workmod_kmm.profile.data

import app.workmod.workmod_kmm.common.Prefs
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
    //private val _profilesUrl = "http://10.0.2.2:5000/api/profile/"

    private val _getUrl = _profilesUrl
    private val _getAllUrl = "${_profilesUrl}all"
    private val _addUrl = _profilesUrl
    private val _updateUrl = _profilesUrl
    private val _deleteUrl = _profilesUrl


    suspend fun getProfile(profileId: String): GetProfileResponse {
        val token = prefs.getToken()
        val response = client.get("${_getUrl}$profileId") {
            contentType(ContentType.Application.Json)
            headers { append("Authorization", "Bearer $token") }
            parameter("profileId", profileId)
        }

        val getProfileResponse: GetProfileResponse = response.body()
        getProfileResponse.statusCode = response.status.value

        return getProfileResponse
    }

    suspend fun getAllProfiles(): GetAllProfilesResponse {
        val token = prefs.getToken()
        val response = client.get(_getAllUrl) {
            contentType(ContentType.Application.Json)
            headers { append("Authorization", "Bearer $token") }
        }

        val getAllProfileResponse: GetAllProfilesResponse = response.body()
        getAllProfileResponse.statusCode = response.status.value

        return getAllProfileResponse
    }

    suspend fun addProfile(
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

        val response = client.post(_addUrl) {
            contentType(ContentType.Application.Json)
            headers { append("Authorization", "Bearer $token") }
            setBody(
                mapOf(
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