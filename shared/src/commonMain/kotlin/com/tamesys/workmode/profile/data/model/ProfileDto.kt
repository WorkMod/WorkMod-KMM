package com.tamesys.workmode.profile.data.model

import com.tamesys.workmode.profile.domain.model.Profile
import com.tamesys.workmode.profile.domain.model.SkillSet
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

    @SerialName("educations") val educations: List<EducationDto> = listOf(),
    @SerialName("employments") val employments: List<EmploymentDto> = listOf(),
    @SerialName("skillSets") val skillSets: List<SkillSetDto> = listOf(),

    @SerialName("interests") val interests: String? = "",
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
        educations = this.educations.map { it.toEducation() }.toMutableList(),
        employments = this.employments.map { it.toEmployment() }.toMutableList(),
        skillSets = this.skillSets.map { it.toSkillSet() },
        interests = if (this.interests?.isNotEmpty() == true) {
            this.interests.split(",").map { it.trim() }
        } else listOf(),
        interestString =  this.interests,
        phone = this.phone,
        address = this.address,
        nationality = this.nationality,
        description = this.description
    )
}


