package com.tamesys.workmode.android.di

import android.content.Context
import com.tamesys.workmode.common.Prefs
import com.tamesys.workmode.common.dataStoreFileName
import org.koin.dsl.module

val prefModule = module {
    single<Prefs> {
        Prefs(producePath = { get<Context>().filesDir.resolve(dataStoreFileName).absolutePath })
    }
}