package com.tamesys.workmode.profile.presentation

import com.tamesys.workmode.profile.domain.model.Profile

data class GetAllProfilesResult(
    val success: Boolean = false,
    val loading: Boolean = false,
    val profiles: List<Profile> = listOf(),
    val error: String = ""
)
