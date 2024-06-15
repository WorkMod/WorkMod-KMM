package app.workmod.workmod_kmm.di

import app.workmod.workmod_kmm.MainViewModel
import app.workmod.workmod_kmm.common.Prefs
import org.koin.dsl.module

val mainModule = module {
    single<Prefs> { Prefs() }
    single<MainViewModel> { MainViewModel(get()) }
}