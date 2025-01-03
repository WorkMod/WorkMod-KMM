package com.tamesys.workmode.profile.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class SkillSet(
    val id: String,
    val title: String,
    val skills: List<String>,
    val description: String
)
