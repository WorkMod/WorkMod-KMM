package app.workmod.workmod_kmm.profile.presentation

import app.workmod.workmod_kmm.profile.data.Profile

data class GetAllProfilesResult(
    val success: Boolean = false,
    val loading: Boolean = false,
    val profiles: List<Profile> = listOf(),
    val error: String = ""
)
