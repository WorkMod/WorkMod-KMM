package app.workmod.workmod_kmm.profile.data.response

import app.workmod.workmod_kmm.profile.data.model.ProfileDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetAllProfilesResponse(
    @SerialName("statusCode") var statusCode: Int = 0,
    @SerialName("profiles") val profiles: List<ProfileDto> = listOf(),
    @SerialName("message") val message: String = ""
)
