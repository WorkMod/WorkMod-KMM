package app.workmod.workmod_kmm.common

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

fun dataStore(context: Context): DataStore<Preferences> {
    return Prefs().createDataStore(
        producePath = { context.filesDir.resolve(dataStoreFileName).absolutePath }
    )
}