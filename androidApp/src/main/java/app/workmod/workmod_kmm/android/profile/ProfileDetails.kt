package app.workmod.workmod_kmm.android.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import app.workmod.workmod_kmm.android.common.ui.DialogOkCancel
import app.workmod.workmod_kmm.profile.domain.model.Profile
import app.workmod.workmod_kmm.profile.presentation.ProfileViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileDetails(navController: NavHostController,
                   showSnack: (message: String) -> Unit,
                   viewModel: ProfileViewModel = koinViewModel(),
                   profileId: String
) {

    val showDeleteDialog = remember { mutableStateOf(false) }
    val profileResult by viewModel.getProfileResult.collectAsState()
    val titleString  = remember { mutableStateOf("") }

    val deleteProfileResult by viewModel.deleteProfileResult.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getProfile(profileId)
    }

    if (deleteProfileResult.success) {
        viewModel.deleteProfileReset()
        navController.popBackStack()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = titleString.value)
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Filled.Menu,"")
                    }
                },
                actions = @Composable {
                    IconButton(onClick = {
                        navController.navigate(Destination.AddProfile.route.replace(
                            oldValue = "{id}",
                            newValue = profileId
                        ))
                    }) {
                        Icon(Icons.Filled.Edit,"Edit")
                    }
                    IconButton(onClick = {
                        showDeleteDialog.value = true
                    }) {
                        Icon(Icons.Filled.Delete,"Delete")
                    }
                },
                /*backgroundColor = Color.Blue,
                contentColor = Color.White,
                elevation = 12.dp*/
            )
        }
    ) { padding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(padding)) {
            if (profileResult.loading || deleteProfileResult.loading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .width(64.dp)
                        .align(Alignment.Center),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
            if (profileResult.success) {
                titleString.value = profileResult.profile?.title ?: ""
                profileResult.profile?.let {profile ->
                    Card(modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()) {
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState())
                            .padding(0.dp)) {
                            ProfileHeader(profile)
                            Spacer(modifier = Modifier.height(12.dp))
                            EmploymentDetails(profile.employments)
                            //Spacer(modifier = Modifier.height(12.dp))
                            EducationDetails(profile.educations)
                            Spacer(modifier = Modifier.height(12.dp))
                            ProfileFooter(profile)
                            EducationDetails(profile.educations)
                        }
                    }
                } ?: run {
                    Text(text = "Empty profile!")
                }
            } else if (profileResult.error.isNotEmpty()) {
                Column(modifier = Modifier
                    .align(Alignment.Center)
                    .padding(20.dp)) {
                    Text(
                        text = "Error loading profile!",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                        onClick = { viewModel.getProfile(profileId) }) {
                        Text(text = "Retry")
                    }
                }
            }

        }

        if (showDeleteDialog.value) {
            DialogOkCancel(
                title = "Delete",
                mesage = "Are you sure to delete this profile?",
                ok = "Okay",
                onOk = {
                    viewModel.deleteProfile(profileId)
                    showDeleteDialog.value = false
                },
                cancel = "Cancel",
                onCancel = { showDeleteDialog.value = false },
                onDismiss = {
                    showDeleteDialog.value = false
                }
            )
        }
    }
}

@Composable
private fun ProfileHeader(profile: Profile) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 20.dp, start = 10.dp, end = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = profile.name)
        Text(text = profile.designation)
        Text(text = profile.email)
    }
}

@Composable
private fun ProfileFooter(profile: Profile) {
    Card(
        /*colors = CardDefaults.cardColors(
        containerColor = Color.Green,)*/
    ) {
        Column(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()) {
            Text(text = profile.phone)
            Text(text = profile.address)
            Text(text = profile.nationality)
            Text(text = profile.description)
        }
    }
}