package com.tamesys.workmode.android

import android.app.Application
import com.tamesys.workmode.android.di.prefModule
import com.tamesys.workmode.android.di.viewModelsModule
import com.tamesys.workmode.di.sharedKoinModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WorkModApp: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        val modules = sharedKoinModules + prefModule + viewModelsModule
        startKoin {
            androidContext(this@WorkModApp)
            modules(modules)
        }
    }
}