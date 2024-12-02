package com.tamesys.workmode.profile.presentation

import com.tamesys.workmode.profile.domain.model.Profile

data class GetProfileResult(
    val success: Boolean = false,
    val loading: Boolean = false,
    val profile: Profile? = null,
    val error: String = ""
)
