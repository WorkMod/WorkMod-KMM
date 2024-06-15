package app.workmod.workmod_kmm.android.di

import app.workmod.workmod_kmm.MainViewModel
import app.workmod.workmod_kmm.auth.presentation.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { AuthViewModel(get(), get()) }
}