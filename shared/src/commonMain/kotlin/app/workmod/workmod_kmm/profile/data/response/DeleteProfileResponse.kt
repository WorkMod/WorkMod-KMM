package app.workmod.workmod_kmm.profile.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeleteProfileResponse(
    @SerialName("statusCode") var statusCode: Int = 0,
    @SerialName("message") val message: String = ""
)
