package com.tamesys.workmode.android.profile

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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.tamesys.workmode.android.common.ui.DialogOkCancel
import com.tamesys.workmode.android.home.Screen
import com.tamesys.workmode.common.BoolState
import com.tamesys.workmode.profile.domain.model.Profile
import com.tamesys.workmode.profile.presentation.DownloadProfileResult
import com.tamesys.workmode.profile.presentation.GetProfileResult
import com.tamesys.workmode.profile.presentation.ProfileViewModel
import org.koin.androidx.compose.koinViewModel
import kotlin.text.Typography.section

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileDetails(navController: NavHostController,
                   showSnack: (message: String) -> Unit,
                   viewModel: ProfileViewModel = koinViewModel(),
                   profileId: String
) {

    val showDeleteDialog = remember { mutableStateOf(false) }
    val profileResult by viewModel.getProfileResult.collectAsState(GetProfileResult())
    val titleString  = remember { mutableStateOf("") }

    val deleteProfileResult by viewModel.deleteProfileResult.collectAsState(BoolState())
    val downloadProfileResult by viewModel.downloadProfileResult.collectAsState(
        DownloadProfileResult()
    )

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.getProfile(profileId)
    }

    if (deleteProfileResult.success) {
        navController.popBackStack()
    }
    if (downloadProfileResult.success) {
        showSnack("CV downloaded" + downloadProfileResult.filePath)
    } else if (downloadProfileResult.error.isNotEmpty()){
        showSnack("CV download error: ${downloadProfileResult.error}")
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = titleString.value)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },
                actions = @Composable {
                    IconButton(onClick = {
                        viewModel.clearExperiences()
                        navController.navigate(Screen.ProfileAdd.route.replace(
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
            if (profileResult.loading
                || deleteProfileResult.loading
                || downloadProfileResult.loading) {
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
                        .padding(0.dp)
                        .fillMaxWidth()) {
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState())
                            .padding(0.dp)) {
                            ProfileHeader(profile)
                            Spacer(modifier = Modifier.height(12.dp))
                            if (profile.employments.isNotEmpty()) {
                                EmploymentDetails(profile.employments)
                            }
                            if (profile.educations.isNotEmpty()) {
                                EducationDetails(profile.educations)
                            }
                            Spacer(modifier = Modifier.height(4.dp))

                            profile.interestString?.let { interests ->
                                if (interests.isNotEmpty()) {
                                    ProfileInterests(interests)
                                }
                            }
                            //Add Skills and Interests section
                            ProfileFooter(profile)
                            Button(modifier = Modifier.padding(8.dp, 8.dp, 8.dp, 16.dp)
                                .align(Alignment.CenterHorizontally), onClick = {
                                viewModel.downloadProfile(profileId, context.filesDir.path)
                            }) {
                                Text("Download CV")
                            }
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
        Text(text = profile.description)
    }
}

@Composable
private fun ProfileInterests(interests: String) {
    Column(modifier = Modifier.padding(16.dp, 0.dp)) {
        Text(
            modifier = Modifier.padding(6.dp),
            text = "Interests",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
        )
        Text(
            modifier = Modifier.padding(6.dp),
            text = interests,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
        )
    }

}

@Composable
private fun ProfileFooter(profile: Profile) {
    Card(
        /*colors = CardDefaults.cardColors(
        containerColor = Color.Green,)*/
    ) {
        Column(modifier = Modifier
            .padding(16.dp, 8.dp)
            .fillMaxWidth()) {
            Text(text = profile.email)
            Text(text = profile.phone)
            Text(text = profile.address)
            Text(text = profile.nationality)
            Text(text = profile.description)
        }
    }
}