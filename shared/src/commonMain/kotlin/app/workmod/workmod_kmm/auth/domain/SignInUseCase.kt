package app.workmod.workmod_kmm.auth.domain

import app.workmod.workmod_kmm.auth.data.AuthService
import app.workmod.workmod_kmm.auth.data.SignInResponse

class SignInUseCase(private val service: AuthService) {

    suspend fun signIn(email: String, password: String): SignInResponse {
        return service.signIn(email, password)
    }
}