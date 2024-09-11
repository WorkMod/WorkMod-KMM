package app.workmod.workmod_kmm.profile.domain

import app.workmod.workmod_kmm.profile.data.response.DeleteProfileResponse
import app.workmod.workmod_kmm.profile.data.ProfileService

class DeleteProfileUseCase(private val service: ProfileService) {

    suspend fun get(profileId: String): DeleteProfileResponse {
        return service.deleteProfile(profileId)
    }
}