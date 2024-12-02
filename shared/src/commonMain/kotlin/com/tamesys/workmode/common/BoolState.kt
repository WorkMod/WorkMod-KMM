package com.tamesys.workmode.common

data class BoolState(
    val success: Boolean = false,
    val loading: Boolean = false,
    val error: String = ""
)
