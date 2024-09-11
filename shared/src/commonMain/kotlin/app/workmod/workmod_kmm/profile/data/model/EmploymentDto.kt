package app.workmod.workmod_kmm.profile.data.model

import app.workmod.workmod_kmm.profile.domain.model.Employment
import kotlinx.serialization.Serializable

@Serializable
data class EmploymentDto(
    val id: String,
    val designation: String,
    val employer: String,
    val from: String,
    val to: String
) {
    fun toEmployment() = Employment(
        id = this.id,
        title = this.designation,
        company = this.employer,
        from = this.from,
        to = this.to
    )
}
