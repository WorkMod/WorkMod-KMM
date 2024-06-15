package app.workmod.workmod_kmm.common

data class BoolState(
    val success: Boolean = false,
    val loading: Boolean = false,
    val error: String = "Unknown error!"
)
