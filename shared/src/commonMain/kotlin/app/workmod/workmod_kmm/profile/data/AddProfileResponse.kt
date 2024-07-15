package app.workmod.workmod_kmm.profile.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddProfileResponse(
    @SerialName("statusCode") var statusCode: Int = 0,
    @SerialName("message") val message: String = ""
)
