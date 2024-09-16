package app.workmod.workmod_kmm.profile.data.model

import app.workmod.workmod_kmm.profile.domain.model.Profile
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileDto(
    @SerialName("id") val id: String,
    @SerialName("userId") val userId: String,
    @SerialName("title") val title: String,
    @SerialName("name") val name: String,
    @SerialName("designation") val designation: String,
    @SerialName("email") val email: String,

    val educations: List<EducationDto> = listOf(),
    val employments: List<EmploymentDto> = listOf(),

    @SerialName("phone") val phone: String,
    @SerialName("address") val address: String,
    @SerialName("nationality") val nationality: String,
    @SerialName("description") val description: String
) {
    fun toProfile() = Profile(
        id = this.id,
        userId = this.userId,
        title = this.title,
        name = this.name,
        designation = this.designation,
        email = this.email,
        educations = this.educations.map { it.toEducation() },
        employments = this.employments.map { it.toEmployment() },
        phone = this.phone,
        address = this.address,
        nationality = this.nationality,
        description = this.description
    )
}


