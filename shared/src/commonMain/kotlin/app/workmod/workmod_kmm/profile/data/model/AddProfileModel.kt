package app.workmod.workmod_kmm.profile.data.model

import app.workmod.workmod_kmm.profile.domain.model.Education
import app.workmod.workmod_kmm.profile.domain.model.Employment
import kotlinx.serialization.Serializable

@Serializable
data class AddProfileModel(
    val profileId: String = "",
    val title: String,
    val name: String,
    val designation: String,
    val email: String,
    val employments: List<Employment>,
    val educations: List<Education>,
    val phone: String,
    val address: String,
    val nationality: String,
    val description: String
)
