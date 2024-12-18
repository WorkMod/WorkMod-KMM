package com.tamesys.workmode.android.profile.add

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.tamesys.workmode.android.profile.Destination
import com.tamesys.workmode.android.profile.EducationDetails
import com.tamesys.workmode.android.profile.EmploymentDetails
import com.tamesys.workmode.android.profile.Screen
import com.tamesys.workmode.common.BoolState
import com.tamesys.workmode.profile.domain.model.Education
import com.tamesys.workmode.profile.domain.model.Employment
import com.tamesys.workmode.profile.presentation.GetProfileResult
import com.tamesys.workmode.profile.presentation.ProfileViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddProfile(navController: NavHostController,
               showSnack: (message: String) -> Unit,
               editProfileId: String? = null,
               viewModel: ProfileViewModel = koinViewModel()
) {

    var title by remember { mutableStateOf("iOS Developer") }
    var name by remember { mutableStateOf("Riyas Edathadathil") }
    var designation by remember { mutableStateOf("iOS Developer") }
    var email by remember { mutableStateOf("riyas@gmail.com") }

    var phone by remember { mutableStateOf("07384184458") }
    var nationality by remember { mutableStateOf("Indian") }
    var address by remember { mutableStateOf("32 Selsdon Road") }
    var description by remember { mutableStateOf("I am an awesome iOS Developer") }

    var employmentList by remember { mutableStateOf(listOf<Employment>()) }

    var educationList by remember { mutableStateOf(listOf<Education>()) }

    var counter by remember { mutableStateOf(0) }

    val newEmployments by viewModel.newEmploymentsResult.collectAsState()
    val newEducations by viewModel.newEducationResult.collectAsState()

    if (editProfileId != null) {
        LaunchedEffect(Unit) {
            viewModel.getProfile(editProfileId)
        }
        val profileResult by viewModel.getProfileResult.collectAsState(GetProfileResult())
        if (profileResult.success) {
            profileResult.profile?.let {
                title = it.title
                name = it.name
                designation = it.designation
                email = it.email
                phone = it.phone
                address = it.address
                nationality = it.nationality
                description = it.description
                educationList = it.educations.toMutableList() + newEducations
                employmentList = it.employments.toMutableList() + newEmployments
            }
        }
    } else {
        employmentList = newEmployments
        educationList = newEducations
    }

    val addProfile by viewModel.addProfileResult.collectAsState(BoolState())
    if (addProfile.success) {
        showSnack("Profile added!")
        navController.popBackStack()
    } else if (addProfile.error.isNotEmpty()) {
        showSnack(addProfile.error)
    }

    val updateProfile by viewModel.updateProfileResult.collectAsState(BoolState())
    if (updateProfile.success) {
        showSnack("Profile updated!")
        navController.popBackStack()
    } else if (updateProfile.error.isNotEmpty()) {
        showSnack(updateProfile.error)
    }


    Surface {
        Box(modifier = Modifier.fillMaxSize()) {
            if (addProfile.loading || updateProfile.loading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .width(64.dp)
                        .align(Alignment.Center),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }

            Column(modifier = Modifier
                .padding(10.dp)
                .verticalScroll(rememberScrollState())
                .align(Alignment.Center)) {
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)) {
                    TextField(value = title,
                        label = { Text(text = "Profile title") },
                        onValueChange = { title = it})
                }

                Divider(modifier = Modifier
                    .width(IntrinsicSize.Min)
                    .height(10.dp))
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column {
                        TextField(value = name,
                            label = { Text(text = "Name") },
                            onValueChange = { name = it})
                        TextField(value = designation,
                            label = { Text(text = "Designation") },
                            onValueChange = { designation = it})
                        TextField(value = email,
                            label = { Text(text = "Email") },
                            onValueChange = { email = it})
                    }
                }

                Divider(modifier = Modifier
                    .width(IntrinsicSize.Min)
                    .height(10.dp))

                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally) {

                        EmploymentDetails(employments = employmentList) {deleteIndex ->
                            val newList = employmentList.toMutableList()
                            newList.removeAt(deleteIndex)
                            employmentList = newList
                        }

                        Button(modifier = Modifier, onClick = {
                            navController.navigate(Destination.AddEmployment.route)
                        }) {
                            Text("Add Employment")
                        }

                        Divider(modifier = Modifier.height(6.dp).padding(vertical = 2.dp))

                        EducationDetails(educations = educationList) {deleteIndex ->
                            val newList = educationList.toMutableList()
                            newList.removeAt(deleteIndex)
                            educationList = newList
                        }
                        Button(modifier = Modifier, onClick = {
                            navController.navigate(Destination.AddEducation.route)
                        }) {
                            Text("Add Education")
                        }
                        Divider(modifier = Modifier.height(6.dp).padding(vertical = 2.dp))
                    }
                }

                Divider(modifier = Modifier
                    .width(IntrinsicSize.Min)
                    .height(10.dp))

                Card(modifier = Modifier.fillMaxWidth()) {
                    Column {
                        TextField(value = phone,
                            label = { Text(text = "Phone") },
                            onValueChange = { phone = it})
                        TextField(value = nationality,
                            label = { Text(text = "Nationality") },
                            onValueChange = { nationality = it})
                        TextField(value = address,
                            label = { Text(text = "Address") },
                            onValueChange = { address = it})
                        TextField(value = description,
                            label = { Text(text = "Description") },
                            onValueChange = { description = it })
                    }
                }

                Button(modifier = Modifier
                    .padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally),
                    onClick = {
                        showSnack.invoke("Hellooo!")
                        if (name.isBlank()) {
                            showSnack.invoke("Enter name!")
                        } else if (designation.isBlank()) {
                            showSnack.invoke("Enter designation!")
                        } else if (email.isBlank()) {
                            showSnack.invoke("Enter email!")
                        } else if (phone.isBlank()) {
                            showSnack.invoke("Enter phone!")
                        } else if (address.isBlank()) {
                            showSnack.invoke("Enter address!")
                        } else if (nationality.isBlank()) {
                            showSnack.invoke("Enter nationality!")
                        } else if (description.isBlank()) {
                            showSnack.invoke("Enter description!")
                        } else {
                            if (editProfileId.isNullOrEmpty()) {
                                Log.d(Screen.AddProfile.route, "viewModel.addProfile()")
                                viewModel.addProfile(
                                    title,
                                    name,
                                    designation,
                                    email,
                                    employmentList,
                                    educationList,
                                    phone,
                                    address,
                                    nationality,
                                    description
                                )
                            } else {
                                viewModel.updateProfile(
                                    editProfileId,
                                    title,
                                    name,
                                    designation,
                                    email,
                                    employmentList,
                                    educationList,
                                    phone,
                                    address,
                                    nationality,
                                    description
                                )
                            }
                        }
                    }) {
                    Text(if (editProfileId.isNullOrEmpty()) "Add Profile" else "Update Profile")
                }
            }
        }
    }
}