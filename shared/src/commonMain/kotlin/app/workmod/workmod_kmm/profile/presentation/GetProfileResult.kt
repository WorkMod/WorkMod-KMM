package app.workmod.workmod_kmm.profile.presentation

import app.workmod.workmod_kmm.profile.domain.model.Profile

data class GetProfileResult(
    val success: Boolean = false,
    val loading: Boolean = false,
    val profile: Profile? = null,
    val error: String = ""
)
