package com.tamesys.workmode.profile.domain

import com.tamesys.workmode.profile.data.ProfileService
import com.tamesys.workmode.profile.data.response.AddProfileResponse
import com.tamesys.workmode.profile.domain.model.Education
import com.tamesys.workmode.profile.domain.model.Employment
import com.tamesys.workmode.profile.domain.model.SkillSet

class UpdateProfileUseCase(private val service: ProfileService) {

    suspend fun get(profileId: String,
                    title: String,
                    name: String,
                    designation: String,
                    email: String,
                    employments: List<Employment>,
                    educations: List<Education>,
                    skillSets: List<SkillSet>,
                    interests: List<String>,
                    phone: String,
                    address: String,
                    nationality: String,
                    description: String): AddProfileResponse {
        return service.updateProfile(profileId, title, name, designation, email,
            employments, educations, skillSets, interests,
            phone, address, nationality, description)
    }
}