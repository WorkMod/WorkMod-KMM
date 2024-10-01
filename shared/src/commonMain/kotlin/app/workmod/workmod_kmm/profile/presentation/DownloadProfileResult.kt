package app.workmod.workmod_kmm.profile.presentation

data class DownloadProfileResult(
    val success: Boolean = false,
    val loading: Boolean = false,
    val percentage: Int = 0,
    val filePath: String? = null,
    val error: String = ""
)
