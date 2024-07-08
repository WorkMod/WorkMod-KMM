package app.workmod.workmod_kmm.common

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import okio.Path.Companion.toPath

const val dataStoreFileName = "workmod.preferences_pb"

class Prefs(producePath: () -> String) {

    private val USER_NAME = stringPreferencesKey("userName")
    private val USER_ID = stringPreferencesKey("userId")
    private val TOKEN = stringPreferencesKey("token")

    private val dataStore: DataStore<Preferences>

    init {
        dataStore = PreferenceDataStoreFactory.createWithPath(
            corruptionHandler = null,
            migrations = emptyList(),
            produceFile = { producePath().toPath() },
        )
    }

    /*
    producePath = { context.filesDir.resolve(dataStoreFileName).absolutePath }
     */
    /*fun createDataStore(
        producePath: () -> String,
    ): DataStore<Preferences> = PreferenceDataStoreFactory.createWithPath(
        corruptionHandler = null,
        migrations = emptyList(),
        produceFile = { producePath().toPath() },
    )*/

    /*fun createDataStore(producePath: () -> String): DataStore<Preferences> {
        dataStore = PreferenceDataStoreFactory.createWithPath(
            corruptionHandler = null,
            migrations = emptyList(),
            produceFile = { producePath().toPath() },
        )
        return dataStore
    }*/

    suspend fun saveLogin(userId: String, userName: String, token: String) {
        dataStore.edit {
            it[USER_ID] = userId
            it[USER_NAME] = userName
            it[TOKEN] = token
        }
    }

    suspend fun isLoggedIn(): Boolean {
        val hasUserId = dataStore.data.map {
            println("isLoggedIn: userId: ${it[USER_ID]}")
            !it[USER_ID].isNullOrEmpty()
        }.first()
        return hasUserId
        /*val userId = preference.getString(USER_ID, "")
        return !userId.isNullOrEmpty()*/
    }

    suspend fun getUserId(): String {
        return dataStore.data.map {
            it[USER_ID] ?: ""
        }.first()
    }

    fun getUserName(): Flow<String> {
        return dataStore.data.map {
            it[USER_NAME] ?: ""
        }
    }

    suspend fun getToken(): String {
        return dataStore.data.map {
            it[TOKEN] ?: ""
        }.first()
    }

}