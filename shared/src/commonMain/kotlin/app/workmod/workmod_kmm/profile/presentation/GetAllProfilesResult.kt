package app.workmod.workmod_kmm.profile.presentation

import app.workmod.workmod_kmm.profile.domain.model.Profile

data class GetAllProfilesResult(
    val success: Boolean = false,
    val loading: Boolean = false,
    val profiles: List<Profile> = listOf(),
    val error: String = ""
)
