package com.tamesys.workmode.profile.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddProfileResponse(
    @SerialName("statusCode") var statusCode: Int = 0,
    @SerialName("message") val message: String = ""
)
