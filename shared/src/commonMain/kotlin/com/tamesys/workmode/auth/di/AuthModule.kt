package com.tamesys.workmode.auth.di

import com.tamesys.workmode.auth.data.AuthService
import com.tamesys.workmode.auth.domain.SignInUseCase
import com.tamesys.workmode.auth.domain.SignUpUseCase
import com.tamesys.workmode.auth.presentation.AuthViewModel
import org.koin.dsl.module

val authModule = module {
    single<AuthService> { AuthService(get()) }
    single<SignUpUseCase> { SignUpUseCase(get()) }
    single<SignInUseCase> { SignInUseCase(get()) }
    single<AuthViewModel> { AuthViewModel(get(), get(), get()) }
}