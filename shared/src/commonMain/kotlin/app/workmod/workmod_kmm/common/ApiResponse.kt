package app.workmod.workmod_kmm.common

data class ApiResponse<T: Any>(
    val code: Int,
    val data: T?,
    val message: String
)