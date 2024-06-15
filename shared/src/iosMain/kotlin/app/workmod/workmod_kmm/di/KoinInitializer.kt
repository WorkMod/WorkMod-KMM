package app.workmod.workmod_kmm.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module

fun initKoin() {
    val modules: List<Module> = sharedKoinModules

    startKoin {
        modules(modules)
    }
}