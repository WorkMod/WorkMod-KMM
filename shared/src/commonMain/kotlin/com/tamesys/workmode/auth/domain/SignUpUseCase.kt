package com.tamesys.workmode.auth.domain

import com.tamesys.workmode.auth.data.AuthService
import com.tamesys.workmode.auth.data.SignUpResponse

class SignUpUseCase(private val service: AuthService) {

    suspend fun signUp(userName: String, email: String, password: String): SignUpResponse {
        return service.signUp(userName, email, password)
    }
}