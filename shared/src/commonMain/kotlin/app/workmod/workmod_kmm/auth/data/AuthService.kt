package app.workmod.workmod_kmm.auth.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class AuthService(private val client: HttpClient) {

    private val AUTH_URL = "http://10.0.2.2:5000/api/"
    private val SIGNUP = "${AUTH_URL}auth/signup"
    private val LOGIN = "${AUTH_URL}auth/login"
    private val LOGOUT = "${AUTH_URL}auth/logout"

    suspend fun signUp(userName: String, email: String, password: String): SignUpResponse {
        val response: SignUpResponse = client.post(SIGNUP) {
            setBody(MultiPartFormDataContent(
                formData {
                    append("name", userName)
                    append("email", email)
                    append("password", password)
                }
            ))
        }.body()
        return response
    }

    suspend fun signIn(email: String, password: String): SignInResponse {
        val response: SignInResponse = client.post(LOGIN) {
            setBody(MultiPartFormDataContent(
                formData {
                    append("email", email)
                    append("password", password)
                }
            ))
        }.body()
        return response
    }

    suspend fun signOut() {
        
    }
}