package com.tamesys.workmode.profile.data.model

import com.tamesys.workmode.profile.domain.model.Education
import com.tamesys.workmode.profile.domain.model.Employment
import kotlinx.serialization.Serializable

@Serializable
data class AddProfileModel(
    val profileId: String = "",
    val title: String,
    val name: String,
    val designation: String,
    val email: String,
    val employments: List<EmploymentDto>,
    val educations: List<EducationDto>,
    val skillSets: List<SkillSetDto>,
    val interests: String,
    val phone: String,
    val address: String,
    val nationality: String,
    val description: String
)
