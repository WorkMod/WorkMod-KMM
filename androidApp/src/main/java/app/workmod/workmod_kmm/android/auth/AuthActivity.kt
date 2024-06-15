package com.keepqueue.workmode.auth.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.workmod.workmod_kmm.android.auth.Destination
import app.workmod.workmod_kmm.android.auth.SignIn
import app.workmod.workmod_kmm.android.auth.SignUp
import kotlinx.coroutines.launch

class AuthActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {

            val navController = rememberNavController()
            val scope = rememberCoroutineScope()
            val snackbarHostState = remember { SnackbarHostState() }

            val showSnack = fun(message: String) {
                scope.launch {
                    snackbarHostState.showSnackbar(message)
                }
            }

            Scaffold(
                snackbarHost = {
                    SnackbarHost(hostState = snackbarHostState)
                }
            ) { padding ->
                NavHost(navController = navController, startDestination = Destination.SignIn.route) {
                    composable(Destination.SignIn.route) {
                        SignIn(navController, showSnack)
                    }
                    composable(Destination.SignUp.route) {
                        SignUp(navController, showSnack)
                    }
                }
            }
        }
    }
}