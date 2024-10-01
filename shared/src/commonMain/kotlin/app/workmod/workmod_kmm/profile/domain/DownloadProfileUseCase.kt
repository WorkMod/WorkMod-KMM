package app.workmod.workmod_kmm.profile.domain

import app.workmod.workmod_kmm.common.ApiResponse
import app.workmod.workmod_kmm.common.FileWriter
import app.workmod.workmod_kmm.profile.data.ProfileService
import app.workmod.workmod_kmm.profile.data.response.DownloadProfileResponse

class DownloadProfileUseCase(private val service: ProfileService) {

    suspend fun get(fileWriter: FileWriter, profileId: String):  ApiResponse<DownloadProfileResponse> {
        return service.downloadProfile(fileWriter, profileId)
    }
}