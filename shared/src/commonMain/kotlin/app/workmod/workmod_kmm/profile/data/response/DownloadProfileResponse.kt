package app.workmod.workmod_kmm.profile.data.response

import kotlinx.serialization.Serializable

@Serializable
data class DownloadProfileResponse(
    var status: Boolean = false,
    val cloudUrl: String = "",
    var localUrl: String = "",
    val message: String = ""
)
