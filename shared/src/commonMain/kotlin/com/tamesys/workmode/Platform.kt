package com.tamesys.workmode

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform