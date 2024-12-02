package com.tamesys.workmode.profile.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Employment(
    val id: String,
    val title: String,
    val company: String,
    val from: String,
    val to: String
)
