package app.workmod.workmod_kmm.profile.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    @SerialName("id") val id: String,
    @SerialName("userId") val userId: String,
    @SerialName("title") val title: String,
    @SerialName("name") val name: String,
    @SerialName("designation") val designation: String,
    @SerialName("email") val email: String,
    @SerialName("phone") val phone: String,
    @SerialName("address") val address: String,
    @SerialName("nationality") val nationality: String,
    @SerialName("description") val description: String
)
