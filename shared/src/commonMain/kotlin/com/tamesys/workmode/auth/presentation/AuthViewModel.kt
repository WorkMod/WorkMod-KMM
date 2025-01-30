package com.tamesys.workmode.auth.presentation

import com.tamesys.workmode.BaseViewModel
import com.tamesys.workmode.auth.domain.SignInUseCase
import com.tamesys.workmode.auth.domain.SignUpUseCase
import com.tamesys.workmode.common.BoolState
import com.tamesys.workmode.common.Prefs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val signUpUseCase: SignUpUseCase,
    private val signInUseCase: SignInUseCase,
    private val prefs: Prefs
): BaseViewModel() {

    private val _signUpResult = MutableSharedFlow<BoolState>()
    val signUpResult = _signUpResult.asSharedFlow()
    private var signUpJob: Job? = null

    private val _signInResult = MutableSharedFlow<BoolState>()
    val signInResult = _signInResult.asSharedFlow()
    private var signInJob: Job? = null

    private val _signOutResult = MutableStateFlow(BoolState())
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

    fun signIn(email: String, password: String) {
        signInJob?.cancel()
        //API working in PostMan but not in android
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