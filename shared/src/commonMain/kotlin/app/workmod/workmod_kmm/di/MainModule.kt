package app.workmod.workmod_kmm.di

import app.workmod.workmod_kmm.MainViewModel
import org.koin.dsl.module

val mainModule = module {
    single<MainViewModel> { MainViewModel(get()) }
}