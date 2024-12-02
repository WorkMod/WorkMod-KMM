package com.tamesys.workmode.profile.domain

import com.tamesys.workmode.common.ApiResponse
import com.tamesys.workmode.common.FileWriter
import com.tamesys.workmode.profile.data.ProfileService
import com.tamesys.workmode.profile.data.response.DownloadProfileResponse

class DownloadProfileUseCase(private val service: ProfileService) {

    suspend fun get(fileWriter: FileWriter, profileId: String):  ApiResponse<DownloadProfileResponse> {
        return service.downloadProfile(fileWriter, profileId)
    }
}