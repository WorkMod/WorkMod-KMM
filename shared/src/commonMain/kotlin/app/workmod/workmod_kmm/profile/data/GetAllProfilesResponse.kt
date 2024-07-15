package app.workmod.workmod_kmm.profile.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetAllProfilesResponse(
    @SerialName("statusCode") var statusCode: Int = 0,
    @SerialName("profiles") val profiles: List<Profile> = listOf(),
    @SerialName("message") val message: String = ""
)
