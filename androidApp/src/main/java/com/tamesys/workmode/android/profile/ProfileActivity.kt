package com.tamesys.workmode.android.profile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tamesys.workmode.android.MyApplicationTheme
import com.tamesys.workmode.profile.presentation.ProfileViewModel
import com.rahbertheadvisor.android.ui.BottomNavigationBar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: ProfileViewModel by viewModel()

        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()

                val scope = rememberCoroutineScope()
                val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
                val snackbarHostState = remember { SnackbarHostState() }

                val showSnack = fun (message: String) {
                    scope.launch {
                        snackbarHostState.showSnackbar(message)
                    }
                }

                Scaffold(
                    bottomBar = { BottomNavigationBar(navController = navController, bottomBarState) }
                ) { padding ->
                    NavHost(modifier = Modifier.padding(padding),
                        navController = navController, startDestination = Screen.AllProfiles.route) {
                        composable(Screen.AllProfiles.route) {
                            bottomBarState.value = true
                            AllProfiles(navController, showSnack)
                            //Profiles(navController, showSnack)
                        }
                        composable(
                            Screen.ProfileDetails.route,
                            arguments = listOf(navArgument("id") { type = NavType.StringType })
                        ) { navEntry ->
                            navEntry.arguments?.getString("id")?.let { id->
                                bottomBarState.value = false
                                //ProfileDetails(navController, showSnack, profileId = id)
                                Text("Profile Details")
                            }
                        }
                        composable(
                            Screen.AddProfile.route,
                            arguments = listOf(navArgument("id") { type = NavType.StringType})
                        ) { navEntry ->
                            navEntry.arguments?.getString("id")?.let { id->
                                if (id == "id") {
                                    //Add profile Crashing
                                    //AddProfile(navController, showSnack)
                                    Text("Profiles-Add")
                                } else {
                                    //AddProfile(navController, showSnack, editProfileId = id)
                                    Text("Profiles-Add")
                                }
                            }
                            bottomBarState.value = false

                        }
                    }
                }
            }
        }
    }
}