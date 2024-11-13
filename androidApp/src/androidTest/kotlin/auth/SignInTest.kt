package auth

import android.content.Context
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import androidx.navigation.NavHostController
import androidx.test.core.app.ApplicationProvider
import app.workmod.workmod_kmm.android.auth.SignIn
import app.workmod.workmod_kmm.auth.presentation.AuthViewModel
import app.workmod.workmod_kmm.common.BoolState
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.junit.Assert
import org.junit.Test

class SignInTest {

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testPackageName() = runComposeUiTest {
        val context = ApplicationProvider.getApplicationContext<Context>()
        Assert.assertEquals("app.workmod.workmod_kmm.android", context.packageName)
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testSignInEmptyFields() = runComposeUiTest {
        val context = ApplicationProvider.getApplicationContext<Context>()

        val viewModel = mockk<AuthViewModel>()
        every { viewModel.signInResult } returns MutableStateFlow(BoolState())
        //every { viewModel.signIn(any(), any()) } returns MutableStateFlow(BoolState(success = true))
        val navController = mockk<NavHostController>()
        val showSnack = fun(message: String) {}
        setContent { SignIn(viewModel, navController, showSnack) }

        onNodeWithText("Sign In").assertExists()
        onNodeWithText("Sign In").performClick()
        //verify(exactly = 1) { showSnack.invoke(any()) }
        //confirmVerified(showSnack)
    }
}