package app.workmod.workmod_kmm.profile.domain

import app.workmod.workmod_kmm.profile.data.GetProfileResponse
import app.workmod.workmod_kmm.profile.data.ProfileService

class GetProfileUseCase(private val service: ProfileService) {

    suspend fun get(profileId: String): GetProfileResponse {
        return service.getProfile(profileId)
    }
}
