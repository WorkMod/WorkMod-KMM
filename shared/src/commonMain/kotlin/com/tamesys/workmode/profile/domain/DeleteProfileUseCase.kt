package com.tamesys.workmode.profile.domain

import com.tamesys.workmode.profile.data.response.DeleteProfileResponse
import com.tamesys.workmode.profile.data.ProfileService

class DeleteProfileUseCase(private val service: ProfileService) {

    suspend fun get(profileId: String): DeleteProfileResponse {
        return service.deleteProfile(profileId)
    }
}