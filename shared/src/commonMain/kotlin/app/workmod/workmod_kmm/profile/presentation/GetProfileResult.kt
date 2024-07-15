package app.workmod.workmod_kmm.profile.presentation

import app.workmod.workmod_kmm.profile.data.Profile

data class GetProfileResult(
    val success: Boolean = false,
    val loading: Boolean = false,
    val profile: Profile? = null,
    val error: String = ""
)
