package app.workmod.workmod_kmm.profile.domain

import app.workmod.workmod_kmm.common.ApiResponse
import app.workmod.workmod_kmm.profile.data.ProfileService
import app.workmod.workmod_kmm.profile.domain.model.Profile

class GetProfileUseCase(private val service: ProfileService) {

    suspend fun get(profileId: String): ApiResponse<Profile> {
        return service.getProfile(profileId)
    }
}
