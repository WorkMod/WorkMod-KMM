package app.workmod.workmod_kmm

import app.workmod.workmod_kmm.common.BoolState
import app.workmod.workmod_kmm.common.Prefs
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel (val prefs: Prefs): BaseViewModel() {

    private val _signOutResult = MutableStateFlow(BoolState())
    val signOutResult = _signOutResult.asStateFlow()

    //private val _loginState = MutableStateFlow(BoolState())
    //val

    fun signOut() {
        _signOutResult.value = BoolState(loading = true)
        scope.launch {
            delay(500L)
            prefs.saveLogin("", "", "")
            _signOutResult.emit(BoolState(success = true))
            //delay(500L)
            //_signOutResult.emit(BoolState())
        }
    }

    fun signOutReset() {
        _signOutResult.value = BoolState()
    }

    suspend fun isLoggedIn(): Boolean {
        return prefs.isLoggedIn()
    }

    fun getUserName(): Flow<String> {
        return prefs.getUserName()
    }
}