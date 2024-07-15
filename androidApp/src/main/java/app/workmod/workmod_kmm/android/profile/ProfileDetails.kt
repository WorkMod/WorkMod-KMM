package app.workmod.workmod_kmm.android.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
        Box(modifier = Modifier.fillMaxSize()) {
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
                Card(modifier = Modifier.padding(16.dp)) {
                    Column {
                        Text(text = profileResult.profile?.title ?: "")
                        Text(text = profileResult.profile?.name ?: "")
                        Text(text = profileResult.profile?.designation ?: "")
                        Text(text = profileResult.profile?.name ?: "")
                        Text(text = profileResult.profile?.phone ?: "")
                        Text(text = profileResult.profile?.address ?: "")
                        Text(text = profileResult.profile?.nationality ?: "")
                        Text(text = profileResult.profile?.description ?: "")
                    }
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