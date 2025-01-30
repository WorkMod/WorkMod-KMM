package com.tamesys.workmode.android

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tamesys.workmode.MainViewModel
import com.tamesys.workmode.android.home.Screen
import com.tamesys.workmode.android.profile.add.AddProfile
import com.tamesys.workmode.android.profile.AllProfiles
import com.tamesys.workmode.android.profile.ProfileDetails
import com.tamesys.workmode.android.settings.Settings
import com.tamesys.workmode.profile.presentation.ProfileViewModel
import com.tamesys.workmode.android.auth.AuthActivity
import com.rahbertheadvisor.android.ui.BottomNavigationBar
import com.tamesys.workmode.android.profile.add.AddEducation
import com.tamesys.workmode.android.profile.add.AddEmployment
import com.tamesys.workmode.android.profile.add.AddInterests
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: MainViewModel by viewModel()
        val profileViewModel: ProfileViewModel by viewModel()
        var isLoggedIn: Boolean
        runBlocking {
            //Test auth apis and fix datastore
            isLoggedIn = viewModel.isLoggedIn()
        }

        if (!isLoggedIn) {
            finish()
            startActivity(Intent(this, AuthActivity::class.java))
            return
        }

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
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState)},
                    bottomBar = { BottomNavigationBar(navController = navController, bottomBarState) },
                ) { padding ->
                    NavHost(modifier = Modifier.padding(padding),
                        navController = navController, startDestination = Screen.Profiles.route) {
                        composable(Screen.Profiles.route) {
                            bottomBarState.value = true
                            AllProfiles(navController, showSnack, profileViewModel)
                            //Profiles(navController, showSnack)
                        }
                        composable(Screen.ProfileDetails.route,
                            arguments = listOf(navArgument("id") { type = NavType.StringType })
                        ) { navEntry ->
                            navEntry.arguments?.getString("id")?.let { id->
                                bottomBarState.value = false
                                ProfileDetails(navController, showSnack, profileViewModel, profileId = id, )
                                //Text("Profile Details")
                            }
                        }
                        composable(Screen.ProfileAdd.route,
                            arguments = listOf(navArgument("id") { type = NavType.StringType})
                        ) { navEntry ->
                            navEntry.arguments?.getString("id")?.let { id->
                                if (id == "id") {
                                    //Add profile Crashing
                                    //AddProfile(navController, showSnack)
                                    //Text("Profiles-Add")
                                    AddProfile(navController, showSnack, viewModel = profileViewModel)
                                } else {
                                    AddProfile(navController, showSnack, viewModel = profileViewModel, editProfileId = id)
                                    //Text("Profiles-Add")
                                }
                            }
                            bottomBarState.value = false

                        }
                        composable(Screen.ProfileAddEmployment.route) {
                            navEntry ->
                            AddEmployment(navController, showSnack, profileViewModel)
                            bottomBarState.value = false
                        }
                        composable(Screen.ProfileAddEducation.route) {
                            navEntry ->
                            AddEducation(navController, showSnack, profileViewModel)
                            bottomBarState.value = false
                        }
                        composable(Screen.ProfileAddInterests.route) {
                                navEntry ->
                            AddInterests(navController, showSnack, profileViewModel)
                            bottomBarState.value = false
                        }
                        composable(Screen.Jobs.route) {
                            bottomBarState.value = true
                            //Jobs(navController, showSnack)
                            Text("Jobs")
                        }
                        composable(Screen.Settings.route) {
                            bottomBarState.value = true
                            //Settings(navController, showSnack)
                            Settings(navController, showSnack, viewModel)
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun GreetingView(text: String) {
    Text(text = text)
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView("Hello, Android!")
    }
}
