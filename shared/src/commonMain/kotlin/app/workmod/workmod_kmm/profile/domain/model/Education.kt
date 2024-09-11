package app.workmod.workmod_kmm.profile.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Education(
    val id: String,
    var title: String,
    var school: String,
    var from: String,
    var to: String
)
