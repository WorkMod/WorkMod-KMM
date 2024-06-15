package app.workmod.workmod_kmm.android

import android.app.Application
import app.workmod.workmod_kmm.android.di.viewModelsModule
import app.workmod.workmod_kmm.di.sharedKoinModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WorkModApp: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        val modules = sharedKoinModules + viewModelsModule
        startKoin {
            androidContext(this@WorkModApp)
            modules(modules)
        }
    }
}