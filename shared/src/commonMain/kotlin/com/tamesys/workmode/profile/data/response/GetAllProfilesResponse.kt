package com.tamesys.workmode.profile.data.response

import com.tamesys.workmode.profile.data.model.ProfileDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetAllProfilesResponse(
    @SerialName("statusCode") var statusCode: Int = 0,
    @SerialName("profiles") val profiles: List<ProfileDto> = listOf(),
    @SerialName("message") val message: String = ""
)
