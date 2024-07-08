package app.workmod.workmod_kmm.android.di

import android.content.Context
import app.workmod.workmod_kmm.common.Prefs
import app.workmod.workmod_kmm.common.dataStoreFileName
import org.koin.dsl.module

val prefModule = module {
    single<Prefs> {
        Prefs(producePath = { get<Context>().filesDir.resolve(dataStoreFileName).absolutePath })
    }
}