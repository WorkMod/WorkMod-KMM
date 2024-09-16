package app.workmod.workmod_kmm.profile.domain

import app.workmod.workmod_kmm.profile.data.ProfileService
import app.workmod.workmod_kmm.profile.data.response.AddProfileResponse
import app.workmod.workmod_kmm.profile.domain.model.Education
import app.workmod.workmod_kmm.profile.domain.model.Employment

class AddProfileUseCase(private val service: ProfileService) {

    suspend fun get(title: String,
                    name: String,
                    designation: String,
                    email: String,
                    employments: List<Employment>,
                    educations: List<Education>,
                    phone: String,
                    address: String,
                    nationality: String,
                    description: String): AddProfileResponse {
        return service.addProfile(title, name, designation, email,
            employments, educations,
            phone, address, nationality, description)
    }
}