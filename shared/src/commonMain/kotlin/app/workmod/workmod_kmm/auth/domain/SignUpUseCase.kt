package app.workmod.workmod_kmm.auth.domain

import app.workmod.workmod_kmm.auth.data.AuthService
import app.workmod.workmod_kmm.auth.data.SignUpResponse

class SignUpUseCase(private val service: AuthService) {

    suspend fun signUp(userName: String, email: String, password: String): SignUpResponse {
        return service.signUp(userName, email, password)
    }
}