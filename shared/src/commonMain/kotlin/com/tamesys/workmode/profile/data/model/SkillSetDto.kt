package com.tamesys.workmode.profile.data.model

import com.tamesys.workmode.profile.domain.model.SkillSet
import kotlinx.serialization.Serializable

@Serializable
data class SkillSetDto(
    val id: String,
    val title: String,
    val skills: String,
    val description: String
) {

    companion object {
        fun fromSkillSet(skillSet: SkillSet) = SkillSetDto(
            id = skillSet.id,
            title = skillSet.title,
            skills = skillSet.skills.joinToString(),
            description = skillSet.description
        )
    }

    fun toSkillSet() = SkillSet(
        id = this.id,
        title = this.title,
        skills = if (this.skills.isNotEmpty()) {
            this.skills.split(",").map { it.trim() }
        } else  listOf(),
        description = this.description
    )

}
