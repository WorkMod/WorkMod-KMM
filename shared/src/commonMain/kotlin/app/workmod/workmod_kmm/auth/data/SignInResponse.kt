package app.workmod.workmod_kmm.auth.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignInResponse(
    @SerialName("statusCode") val statusCode: Int,
    @SerialName("userId") val userId: String,
    @SerialName("userName") val userName: String,
    @SerialName("token") val token: String,
    @SerialName("message") val message: String
)
