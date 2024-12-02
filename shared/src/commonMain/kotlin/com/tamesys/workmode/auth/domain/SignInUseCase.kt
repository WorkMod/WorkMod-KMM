package com.tamesys.workmode.auth.domain

import com.tamesys.workmode.auth.data.AuthService
import com.tamesys.workmode.auth.data.SignInResponse

class SignInUseCase(private val service: AuthService) {

    suspend fun signIn(email: String, password: String): SignInResponse {
        return service.signIn(email, password)
    }
}