package com.tamesys.workmode.auth.data

import com.tamesys.workmode.common.Constants
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AuthService(private val client: HttpClient) {

    private val AUTH_URL = "${Constants.BASE_URL}/api/auth"
    //private val AUTH_URL = "http://192.168.1.229:5000/api/auth"
    //private val AUTH_URL = "http://10.0.2.2:5000/api/auth"
    private val SIGNUP_URL = "${AUTH_URL}/signup"
    private val LOGIN_URL = "${AUTH_URL}/login"
    private val LOGOUT_URL = "${AUTH_URL}/logout"

    suspend fun signUp(userName: String, email: String, password: String): SignUpResponse {
        val response = client.post(SIGNUP_URL) {
            contentType(ContentType.Application.Json)
            setBody(mapOf("name" to userName,
                    "email" to email,
                    "password" to password))
        }

        val signUpResponse: SignUpResponse = response.body()
        signUpResponse.statusCode = response.status.value

        return signUpResponse
    }

    suspend fun signIn(email: String, password: String): SignInResponse {
        val response = client.post(LOGIN_URL) {
            contentType(ContentType.Application.Json)
            setBody(mapOf("email" to email,
                "password" to password))
        }

        val signInResponse: SignInResponse = response.body()
        signInResponse.statusCode = response.status.value

        return signInResponse
    }

    suspend fun signOut() {
        
    }
}