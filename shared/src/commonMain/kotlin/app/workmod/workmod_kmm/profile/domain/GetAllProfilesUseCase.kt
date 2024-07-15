package app.workmod.workmod_kmm.profile.domain

import app.workmod.workmod_kmm.profile.data.GetAllProfilesResponse
import app.workmod.workmod_kmm.profile.data.ProfileService

class GetAllProfilesUseCase(private val service: ProfileService) {

    suspend fun get(): GetAllProfilesResponse {
        return service.getAllProfiles()
    }
}