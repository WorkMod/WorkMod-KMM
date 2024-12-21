package com.tamesys.workmode.android.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.tamesys.workmode.profile.presentation.GetAllProfilesResult
import com.tamesys.workmode.profile.presentation.ProfileViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AllProfiles(navController: NavHostController,
         showSnack: (message: String) -> Unit,
         viewModel: ProfileViewModel = koinViewModel()
) {

    val context = LocalContext.current

    val allProfiles by viewModel.getAllProfilesResult.collectAsState(GetAllProfilesResult())

    LaunchedEffect(Unit) {
        viewModel.getAllProfiles()
    }

    Scaffold(modifier = Modifier.padding(bottom = 0.dp),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.clearExperiences()
                    navController.navigate(Destination.AddProfile.route.replace(
                        oldValue = "{id}",
                        newValue = "id"
                    ))
                }
            ) {
                Icon(Icons.Filled.Add,"Add profile")
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            if (allProfiles.success) {
                Card(modifier = Modifier.padding(10.dp)) {
                    LazyColumn {
                        items(allProfiles.profiles) { profile ->
                            ProfileItem(profile, navController)
                        }
                    }
                }
            }
            if (allProfiles.loading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .width(64.dp)
                        .align(Alignment.Center),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
            if (allProfiles.error.isNotEmpty() && !allProfiles.success) {
                Column(modifier = Modifier
                    .align(Alignment.Center)
                    .padding(20.dp)) {
                    Text(text = "Error loading profiles!", modifier = Modifier.align(Alignment.CenterHorizontally))
                    Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                        onClick = { viewModel.getAllProfiles() }) {
                        Text(text = "Retry")
                    }
                }
            }

        }
    }
}