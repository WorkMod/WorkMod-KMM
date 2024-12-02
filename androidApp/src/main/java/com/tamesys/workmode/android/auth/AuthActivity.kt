package com.tamesys.workmode.android.auth

import android.annotation.SuppressLint
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
import com.tamesys.workmode.android.auth.Destination
import com.tamesys.workmode.android.auth.SignIn
import com.tamesys.workmode.android.auth.SignUp
import com.tamesys.workmode.auth.presentation.AuthViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthActivity: ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val viewModel: AuthViewModel by viewModel()
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
                        SignIn(viewModel, navController, showSnack)
                    }
                    composable(Destination.SignUp.route) {
                        SignUp(viewModel, navController, showSnack)
                    }
                }
            }
        }
    }
}