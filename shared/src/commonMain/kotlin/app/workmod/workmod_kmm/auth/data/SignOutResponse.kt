package app.workmod.workmod_kmm.auth.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignOutResponse(
    @SerialName("statusCode") val statusCode: Int,
    @SerialName("message") val message: String
)
