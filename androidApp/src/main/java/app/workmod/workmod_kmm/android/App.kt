package app.workmod.workmod_kmm.android

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import app.workmod.workmod_kmm.common.Prefs
import app.workmod.workmod_kmm.common.dataStoreFileName

class App: Application() {

    override fun onCreate() {
        super.onCreate()

    }

    fun dataStore(context: Context): DataStore<Preferences> =
        Prefs().createDataStore(
            producePath = { context.filesDir.resolve(dataStoreFileName).absolutePath }
        )
}