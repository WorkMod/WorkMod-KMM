package app.workmod.workmod_kmm.auth.data

import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

//import io.

@ExperimentalCoroutinesApi
class AuthServiceTest {

    @RelaxedMockK
    lateinit var client: HttpClient

    lateinit var authService: AuthService

    //https://github.com/mockk/mockk/issues/767
    @Test fun signupTest()  = runBlocking {

        val userName = "name"
        val email = "email"
        val password = "password"

        client = mockk<HttpClient>(relaxed = true)
        authService = AuthService(client)

        //val response = SignUpResponse(statusCode = 201)

        lateinit var postUrl: String
        lateinit var builder: HttpRequestBuilder.() -> Unit
        val builderHttp: HttpRequestBuilder.() -> Unit = {
            contentType(ContentType.Application.Json)
            setBody(mapOf("name" to userName,
                "email" to email,
                "password" to password))
        }
        coEvery {
            postUrl = any<String>()
            builder = any<HttpRequestBuilder.() -> Unit>()
            client.post(postUrl, builder)
        } returns client.post(postUrl, builder)

        authService.signUp(userName, email, password)

        coVerify { client.post(postUrl, builder) }
    }
}