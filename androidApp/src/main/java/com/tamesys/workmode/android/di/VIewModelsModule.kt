package com.tamesys.workmode.android.di

import com.tamesys.workmode.MainViewModel
import com.tamesys.workmode.auth.presentation.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { AuthViewModel(get(), get(), get()) }
}