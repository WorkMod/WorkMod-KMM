package com.tamesys.workmode.common

data class ApiResponse<T: Any>(
    val code: Int,
    val data: T?,
    val message: String
)