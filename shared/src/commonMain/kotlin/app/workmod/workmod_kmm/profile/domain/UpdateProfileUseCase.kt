package app.workmod.workmod_kmm.profile.domain

import app.workmod.workmod_kmm.profile.data.AddProfileResponse
import app.workmod.workmod_kmm.profile.data.ProfileService

class UpdateProfileUseCase(private val service: ProfileService) {

    suspend fun get(profileId: String,
                    title: String,
                    name: String,
                    designation: String,
                    email: String,
                    phone: String,
                    address: String,
                    nationality: String,
                    description: String): AddProfileResponse {
        return service.updateProfile(profileId, title, name, designation, email, phone, address, nationality, description)
    }
}