package app.workmod.workmod_kmm.profile.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetProfileResponse(
    @SerialName("statusCode") var statusCode: Int = 0,
    @SerialName("profile") val profile: Profile? = null,
    @SerialName("message") val message: String = ""
)
