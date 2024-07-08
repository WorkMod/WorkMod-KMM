package app.workmod.workmod_kmm.auth.presentation

import app.workmod.workmod_kmm.BaseViewModel
import app.workmod.workmod_kmm.auth.domain.SignInUseCase
import app.workmod.workmod_kmm.auth.domain.SignUpUseCase
import app.workmod.workmod_kmm.common.BoolState
import app.workmod.workmod_kmm.common.Prefs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val signUpUseCase: SignUpUseCase,
    private val signInUseCase: SignInUseCase,
    private val prefs: Prefs
): BaseViewModel() {

    private val _signUpResult = MutableStateFlow<BoolState>(BoolState())
    val signUpResult = _signUpResult.asStateFlow()
    var signUpJob: Job? = null

    private val _signInResult = MutableStateFlow<BoolState>(BoolState())
    val signInResult = _signInResult.asStateFlow()
    var signInJob: Job? = null

    private val _signOutResult = MutableStateFlow<BoolState>(BoolState())
    val signOutResult = _signOutResult.asStateFlow()
    var signOutJob: Job? = null

    //INTEGRATE DATASTORE

    fun signUp(name: String, email: String, password: String) {
        signUpJob?.cancel()
        signUpJob = scope.launch(Dispatchers.IO) {
            try {
                val response = signUpUseCase.signUp(name, email, password)
                if (response.statusCode == 201) {
                    prefs.saveLogin(response.userId, response.userName, response.token)
                    _signUpResult.emit(BoolState(success = true, false))
                } else {
                    _signUpResult.emit(BoolState(success = false, loading = false, error = response.message))
                }
            } catch (e: Exception) {
                _signUpResult.emit(BoolState(success = false, loading = false, error = e.message ?: ""))
            }
        }
    }

    fun signUpReset() {
        _signUpResult.value = BoolState()
    }

    fun signIn(email: String, password: String) {
        signInJob?.cancel()
        signInJob = scope.launch(Dispatchers.IO) {
            try {
                val response = signInUseCase.signIn(email, password)
                if (response.statusCode == 200) {
                    prefs.saveLogin(response.userId, response.userName, response.token)
                    _signInResult.emit(BoolState(success = true, false))
                } else {
                    _signInResult.emit(BoolState(success = false, loading = false, error = response.message))
                }
            } catch (e: Exception) {
                _signInResult.emit(BoolState(success = false, loading = false, error = e.message ?: ""))
            }
        }
    }

    fun signInReset() {
        _signInResult.value = BoolState()
    }

    /*fun signOut() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = authService.signOut(prefs.getToken())
                if (response.isSuccessful) {
                    prefs.saveLogin("", "")
                    _signOutResult.value = Result.Success(true)
                } else {
                    response.errorBody()?.let {
                        _signOutResult.value = Result.Error(it.string())
                    }
                }
            } catch (e: Exception) {
                _signOutResult.value = Result.Error(e.message ?: "Unknown error")
            }
        }
    }*/
}