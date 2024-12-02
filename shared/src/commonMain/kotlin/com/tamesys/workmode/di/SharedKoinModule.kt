package com.tamesys.workmode.di

import com.tamesys.workmode.auth.di.authModule
import com.tamesys.workmode.profile.di.profileModule

val sharedKoinModules = listOf(
    mainModule,
    authModule,
    profileModule,
    networkModule
)