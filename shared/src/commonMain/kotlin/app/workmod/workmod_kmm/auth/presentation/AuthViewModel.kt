package app.workmod.workmod_kmm.auth.presentation

import app.workmod.workmod_kmm.BaseViewModel
import app.workmod.workmod_kmm.auth.domain.SignInUseCase
import app.workmod.workmod_kmm.auth.domain.SignUpUseCase
import app.workmod.workmod_kmm.common.BoolState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val signUpUseCase: SignUpUseCase,
    private val signInUseCase: SignInUseCase
): BaseViewModel() {

    private val _signUpResult = MutableStateFlow<BoolState>(BoolState())
    val signUpResult = _signUpResult.asStateFlow()

    private val _signInResult = MutableStateFlow<BoolState>(BoolState())
    val signInResult = _signInResult.asStateFlow()

    private val _signOutResult = MutableStateFlow<BoolState>(BoolState())
    val signOutResult = _signOutResult.asStateFlow()

    //INTEGRATE DATASTORE

    fun signUp(name: String, email: String, password: String) {
        scope.launch(Dispatchers.IO) {
            try {
                val response = signUpUseCase.signUp(name, email, password)
                if (response.statusCode == 201) {
                    _signUpResult.emit(BoolState(success = true, false))
                } else {
                    _signUpResult.emit(BoolState(success = false, loading = false, error = response.message))
                }
            } catch (e: Exception) {
                _signUpResult.emit(BoolState(success = false, loading = false, error = e.message ?: ""))
            }
        }
    }

    fun signIn(email: String, password: String) {
        scope.launch(Dispatchers.IO) {
            try {
                val response = signInUseCase.signIn(email, password)
                if (response.statusCode == 201) {
                    _signInResult.emit(BoolState(success = true, false))
                } else {
                    _signInResult.emit(BoolState(success = false, loading = false, error = response.message))
                }
            } catch (e: Exception) {
                _signUpResult.emit(BoolState(success = false, loading = false, error = e.message ?: ""))
            }
        }
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