package com.tamesys.workmode.profile.domain

import com.tamesys.workmode.common.ApiResponse
import com.tamesys.workmode.profile.data.ProfileService
import com.tamesys.workmode.profile.domain.model.Profile

class GetAllProfilesUseCase(private val service: ProfileService) {

    suspend fun get(): ApiResponse<List<Profile>> {
        return service.getAllProfiles()
    }
}