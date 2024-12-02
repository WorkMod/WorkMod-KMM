package com.tamesys.workmode.profile.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DownloadProfileResponse(
    var status: Boolean = false,
    @SerialName("fileName") val cloudFileName: String = "",
    var localUrl: String = "",
    val message: String = ""
)
