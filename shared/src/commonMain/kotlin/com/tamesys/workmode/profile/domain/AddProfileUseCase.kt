package com.tamesys.workmode.profile.domain

import com.tamesys.workmode.profile.data.ProfileService
import com.tamesys.workmode.profile.data.response.AddProfileResponse
import com.tamesys.workmode.profile.domain.model.Education
import com.tamesys.workmode.profile.domain.model.Employment

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