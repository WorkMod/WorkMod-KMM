package com.tamesys.workmode.di

import com.tamesys.workmode.MainViewModel
import org.koin.dsl.module

val mainModule = module {
    single<MainViewModel> { MainViewModel(get()) }
}