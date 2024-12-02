package com.tamesys.workmode.di

import com.tamesys.workmode.MainViewModel
import com.tamesys.workmode.auth.presentation.AuthViewModel
import com.tamesys.workmode.profile.presentation.ProfileViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.module.Module

fun initKoin() {
    val modules: List<Module> = sharedKoinModules + IOSPrefModule

    startKoin {
        modules(modules)
    }
}

class AuthInjector: KoinComponent {
    val authViewModel: AuthViewModel by inject()
}

class ProfileInjector: KoinComponent {
    val profileViewModel: ProfileViewModel by inject()
}

class MainInjector: KoinComponent {
    val mainViewModel: MainViewModel by inject()
}