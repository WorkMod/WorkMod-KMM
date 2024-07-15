package app.workmod.workmod_kmm.di

import app.workmod.workmod_kmm.auth.di.authModule
import app.workmod.workmod_kmm.profile.di.profileModule

val sharedKoinModules = listOf(
    mainModule,
    authModule,
    profileModule,
    networkModule
)