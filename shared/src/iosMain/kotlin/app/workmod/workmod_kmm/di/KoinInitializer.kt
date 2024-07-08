package app.workmod.workmod_kmm.di

import app.workmod.workmod_kmm.MainViewModel
import app.workmod.workmod_kmm.auth.presentation.AuthViewModel
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

class MainInjector: KoinComponent {
    val mainViewModel: MainViewModel by inject()
}