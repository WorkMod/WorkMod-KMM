package com.tamesys.workmode.android.profile.add

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowOverflow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.tamesys.workmode.android.home.Screen
import com.tamesys.workmode.android.profile.EducationDetails
import com.tamesys.workmode.android.profile.EmploymentDetails
import com.tamesys.workmode.android.profile.SkillSetDetails
import com.tamesys.workmode.common.BoolState
import com.tamesys.workmode.profile.domain.model.SkillSet
import com.tamesys.workmode.profile.presentation.ProfileViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddProfile(
    navController: NavHostController,
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

    val employmentList by viewModel.employments.collectAsState()
    val educationList by viewModel.educations.collectAsState()

    val skillSetList by viewModel.skills.collectAsState()
    val interestList by viewModel.interests.collectAsState()


    if (editProfileId != null) {
        LaunchedEffect(editProfileId) {
            viewModel.loadFromProfile()
        }
        viewModel.getCachedProfile()?.let {
            title = it.title
            name = it.name
            designation = it.designation
            email = it.email
            phone = it.phone
            address = it.address
            nationality = it.nationality
            description = it.description
        }
    }

    val addProfile by viewModel.addProfileResult.collectAsState(BoolState())
    if (addProfile.success) {
        showSnack("Profile added!")
        navController.navigateUp()
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

            Column(
                modifier = Modifier
                    .padding(16.dp, 0.dp)
                    .verticalScroll(rememberScrollState())
                    .align(Alignment.Center)
            ) {
                Card(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
                    Column {
                        TextField(value = title,
                            label = { Text(text = "Profile title") },
                            onValueChange = { title = it })
                        TextField(value = name,
                            label = { Text(text = "Name") },
                            onValueChange = { name = it })
                        TextField(value = designation,
                            label = { Text(text = "Designation") },
                            onValueChange = { designation = it })
                        TextField(value = email,
                            label = { Text(text = "Email") },
                            onValueChange = { email = it })
                    }
                }

                Divider(
                    modifier = Modifier
                        .width(IntrinsicSize.Min)
                        .height(10.dp)
                )

                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        EmploymentDetails(employments = employmentList) { deleteIndex ->
                            viewModel.removeEmployment(deleteIndex)
                        }
                        Button(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 8.dp), onClick = {
                            navController.navigate(Screen.ProfileAddEmployment.route)
                        }) {
                            Text("Add Employment")
                        }
                        Divider(modifier = Modifier
                            .height(6.dp)
                            .padding(vertical = 2.dp))

                        EducationDetails(educations = educationList) { deleteIndex ->
                            viewModel.removeEducation(deleteIndex)
                        }
                        Button(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 8.dp), onClick = {
                            navController.navigate(Screen.ProfileAddEducation.route)
                        }) {
                            Text("Add Education")
                        }
                    }
                }

                Divider(
                    modifier = Modifier
                        .width(IntrinsicSize.Min)
                        .height(10.dp)
                )

                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        SkillSetDetails(skillSets = skillSetList) { deleteIndex ->
                            viewModel.removeSkillSet(deleteIndex)
                        }
                        Button(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 8.dp), onClick = {
                            navController.navigate(Screen.ProfileAddSkillSet.route)
                        }) {
                            Text("Add SkillSet")
                        }
                        Divider(modifier = Modifier
                            .height(6.dp)
                            .padding(vertical = 2.dp))

                        ProfileInterests(navController, interestList)
                    }
                }



                Divider(
                    modifier = Modifier
                        .width(IntrinsicSize.Min)
                        .height(10.dp)
                )

                Card(modifier = Modifier.fillMaxWidth()) {
                    Column {
                        TextField(value = phone,
                            label = { Text(text = "Phone") },
                            onValueChange = { phone = it })
                        TextField(value = nationality,
                            label = { Text(text = "Nationality") },
                            onValueChange = { nationality = it })
                        TextField(value = address,
                            label = { Text(text = "Address") },
                            onValueChange = { address = it })
                        TextField(value = description,
                            label = { Text(text = "Description") },
                            onValueChange = { description = it })
                    }
                }

                Button(modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.CenterHorizontally),
                    onClick = {
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
                                Log.d(Screen.ProfileAdd.route, "viewModel.addProfile()")
                                viewModel.addProfile(
                                    title,
                                    name,
                                    designation,
                                    email,
                                    employmentList,
                                    educationList,
                                    skillSetList,
                                    interestList,
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
                                    skillSetList,
                                    interestList,
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ProfileInterests(navController: NavHostController ,interestList: List<String>) {
    Column(modifier = Modifier.padding(16.dp, 16.dp)
        .fillMaxWidth()

        ) {
        Text(text = "Interests",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
        )
        if (interestList.isNotEmpty()) {
            FlowRow(
                modifier = Modifier
                    .safeDrawingPadding()
                    .heightIn(min = 0.dp, max = 300.dp)
                    .fillMaxWidth(1f)
                    //.padding(8.dp)
                    .wrapContentHeight(align = Alignment.Top)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                maxLines = 4,
                overflow = FlowRowOverflow.expandIndicator {
                    Text("More",
                        color = Color.Blue,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(5.dp, 0.dp)
                            .clickable {
                                navController.navigate(Screen.ProfileAddInterests.route)
                            })
                }
            ) {
                for (item in interestList) {
                    ChipItem(item)
                }
            }
        } else {
            Text("No Interests added!")
        }
        Button(modifier = Modifier
            .padding(top = 8.dp)
            .align(Alignment.CenterHorizontally), onClick = {
            navController.navigate(Screen.ProfileAddInterests.route)
        }) {
            Text("Add Interests")
        }
    }
}