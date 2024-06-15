package app.workmod.workmod_kmm.auth.di

import app.workmod.workmod_kmm.auth.data.AuthService
import app.workmod.workmod_kmm.auth.domain.SignInUseCase
import app.workmod.workmod_kmm.auth.domain.SignUpUseCase
import app.workmod.workmod_kmm.auth.presentation.AuthViewModel
import org.koin.dsl.module

val authModule = module {
    single<AuthService> { AuthService(get()) }
    single<SignUpUseCase> { SignUpUseCase(get()) }
    single<SignInUseCase> { SignInUseCase(get()) }
    single<AuthViewModel> { AuthViewModel(get(), get()) }
}