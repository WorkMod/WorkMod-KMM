package app.workmod.workmod_kmm.profile.data.response

import app.workmod.workmod_kmm.profile.data.model.ProfileDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetProfileResponse(
    @SerialName("statusCode") var statusCode: Int = 0,
    @SerialName("profile") val profile: ProfileDto? = null,
    @SerialName("message") val message: String = ""
)