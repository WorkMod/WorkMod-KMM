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
    fun toEmployment() = Employment(
        id = this.id,
        title = this.title,
        company = this.company,
        from = this.from,
        to = this.to
    )
}
