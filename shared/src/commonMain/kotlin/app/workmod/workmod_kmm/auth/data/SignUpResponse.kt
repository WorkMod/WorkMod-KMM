package app.workmod.workmod_kmm.auth.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpResponse(
    @SerialName("statusCode") var statusCode: Int = 0,
    @SerialName("userId") val userId: String = "",
    @SerialName("name") val userName: String = "",
    @SerialName("token") val token: String = "",
    @SerialName("message") val message: String = ""
)
