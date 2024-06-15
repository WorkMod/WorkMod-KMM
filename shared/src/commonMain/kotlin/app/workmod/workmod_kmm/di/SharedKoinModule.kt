package app.workmod.workmod_kmm.di

import app.workmod.workmod_kmm.auth.di.authModule

val sharedKoinModules = listOf(
    mainModule,
    authModule,
    networkModule
)