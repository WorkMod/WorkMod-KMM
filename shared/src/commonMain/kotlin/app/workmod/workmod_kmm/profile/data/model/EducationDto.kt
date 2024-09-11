package app.workmod.workmod_kmm.profile.data.model

import app.workmod.workmod_kmm.profile.domain.model.Education
import kotlinx.serialization.Serializable

@Serializable
data class EducationDto(
    val id: String,
    val title: String,
    val school: String,
    val from: String,
    val to: String
) {
    fun toEducation() = Education(
        id = this.id,
        title = this.title,
        school = this.school,
        from = this.from,
        to = this.to
    )
}
