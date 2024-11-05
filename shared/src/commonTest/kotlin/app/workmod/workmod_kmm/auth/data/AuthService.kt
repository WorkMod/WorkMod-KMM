package app.workmod.workmod_kmm.auth.data

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlin.test.AfterTest
import kotlin.test.Asserter
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

//import io.

@ExperimentalCoroutinesApi
class AuthServiceTest {

    //@RelaxedMockK
    lateinit var client: HttpClient
    //val httpResponse = mockk<HttpResponse>()

    lateinit var authService: AuthService

    @BeforeTest fun setup() {

    }

    @AfterTest fun tearDown() {

    }


    //https://github.com/mockk/mockk/issues/767
    /*@Test fun mockTest() = runBlocking {
        val userName = "name"
        val email = "email"
        val password = "password"

        client = mockk<HttpClient>(relaxed = true)

        authService = AuthService(client)

        lateinit var postUrl: String
        lateinit var builder: HttpRequestBuilder.() -> Unit
        val builderHttp: HttpRequestBuilder.() -> Unit = {
            contentType(ContentType.Application.Json)
            setBody(mapOf("name" to userName,
                "email" to email,
                "password" to password))
        }
        val clientResponse = client.post(any<String>(), builder>)
        coEvery {
            postUrl = any<String>()
            builder = any<HttpRequestBuilder.() -> Unit>()
            client.post(any<String>(), any())
        } returns clientResponse

        val response = authService.signUp(userName, email, password)
        coVerify { client.post(postUrl, builder) }
    }*/

    @Test fun signupTest()  = runBlocking {

        val userName = "name"
        val email = "email"
        val password = "password"

        val engine = MockEngine { request ->
            respond(
                content = ByteReadChannel(getSignupResponse()),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        client = HttpClient(engine) {
            install(ContentNegotiation) {
                json()
            }
        }

        authService = AuthService(client)

        val response = authService.signUp(userName, email, password)

        assertEquals("123", response.userId)
    }

    private fun getSignupResponse() = """
         {
            "statusCode": 200,
            "userId": "123",
            "name": "Riyas",
            "token": "ABC",
            "message": "Success"
         }
    """.trimIndent()
}