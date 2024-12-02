package com.tamesys.workmode.profile.domain

import com.tamesys.workmode.common.ApiResponse
import com.tamesys.workmode.profile.data.ProfileService
import com.tamesys.workmode.profile.domain.model.Profile

class GetProfileUseCase(private val service: ProfileService) {

    suspend fun get(profileId: String): ApiResponse<Profile> {
        return service.getProfile(profileId)
    }
}
