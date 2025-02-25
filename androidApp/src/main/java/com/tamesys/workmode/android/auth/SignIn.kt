package com.tamesys.workmode.android.auth

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.tamesys.workmode.android.MainActivity
import com.tamesys.workmode.auth.presentation.AuthViewModel
import com.tamesys.workmode.common.BoolState
import org.koin.androidx.compose.koinViewModel

@Composable fun SignIn(viewModel: AuthViewModel = koinViewModel(),
                       navController: NavHostController,
                       showSnack: (message: String) -> Unit) {

    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val signInResult by viewModel.signInResult.collectAsState(BoolState())

    if (signInResult.success) {
        (context as Activity).finish()
        context.startActivity(Intent(context, MainActivity::class.java))
    } else if (signInResult.error.isNotEmpty()) {
        showSnack(signInResult.error)
    }

    Surface {
        Box(modifier = Modifier.fillMaxSize()) {
            if (signInResult.loading) {
                CircularProgressIndicator(
                    modifier = Modifier.width(64.dp).align(Alignment.Center),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
            Card(modifier = Modifier.align(Alignment.Center)) {
                Column(modifier = Modifier.padding(10.dp)) {
                    TextField(value = email,
                        placeholder = { Text(text = "Enter email") },
                        onValueChange = { email = it})
                    TextField(value = password,
                        placeholder = { Text(text = "Enter password") },
                        onValueChange = { password = it})
                    Button(modifier = Modifier
                        .padding(top = 10.dp)
                        .align(Alignment.CenterHorizontally),
                        onClick = {
                            if (email.isBlank()) {
                                showSnack.invoke("Enter email!")
                            } else if (password.isBlank()) {
                                showSnack.invoke("Enter password!")
                            } else {
                                viewModel.signIn(email, password)
                            }
                        }) {
                        Text("Sign In")
                    }
                    Text(modifier = Modifier
                        .padding(top = 40.dp)
                        .align(Alignment.CenterHorizontally),
                        text = "Don't have an account?")
                    Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                        onClick = {
                            navController.navigate(Destination.SignUp.route) {
                                popUpTo(Destination.SignIn.route) { inclusive = true }
                            }
                        }) {
                        Text("Sign Up")
                    }
                }
            }
        }
    }
}