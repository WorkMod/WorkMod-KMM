package com.tamesys.workmode.profile.domain.model

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

    val educations: List<Education> = listOf(),
    val employments: List<Employment> = listOf(),
    val skillSets: List<SkillSet> = listOf(),

    val interests: List<String> = listOf(),
    val interestString: String?,
    @SerialName("phone") val phone: String,
    @SerialName("address") val address: String,
    @SerialName("nationality") val nationality: String,
    @SerialName("description") val description: String
)
