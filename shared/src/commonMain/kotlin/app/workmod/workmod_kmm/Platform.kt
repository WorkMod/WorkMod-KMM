package app.workmod.workmod_kmm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform