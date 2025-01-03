package com.tamesys.workmode.profile.data.model

import com.tamesys.workmode.profile.domain.model.Employment
import kotlinx.serialization.Serializable

@Serializable
data class EmploymentDto(
    val id: String,
    val title: String,
    val company: String,
    val from: String,
    val to: String
) {
    companion object {
        fun fromEmployment(employment: Employment) = EmploymentDto(
            id = employment.id,
            title = employment.title,
            company = employment.company,
            from = employment.from,
            to = employment.to
        )
    }

    fun toEmployment() = Employment(
        id = this.id,
        title = this.title,
        company = this.company,
        from = this.from,
        to = this.to
    )

}
