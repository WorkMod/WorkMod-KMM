package app.workmod.workmod_kmm.android.profile

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import app.workmod.workmod_kmm.profile.presentation.ProfileViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddProfile(navController: NavHostController,
               showSnack: (message: String) -> Unit,
               editProfileId: String? = null,
               viewModel: ProfileViewModel = koinViewModel()
) {

    val context = LocalContext.current
    var title by remember { mutableStateOf("iOS Developer") }
    var name by remember { mutableStateOf("Riyas Edathadathil") }
    var designation by remember { mutableStateOf("iOS Developer") }
    var email by remember { mutableStateOf("riyas@gmail.com") }
    var phone by remember { mutableStateOf("07384184458") }
    var address by remember { mutableStateOf("32 Selsdon Road") }
    var nationality by remember { mutableStateOf("Indian") }
    var description by remember { mutableStateOf("I am an awesome iOS Developer") }

    editProfileId?.let {
        LaunchedEffect(Unit) {
            viewModel.getProfile(editProfileId)
        }
        val profileResult by viewModel.getProfileResult.collectAsState()
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
            }
        }
    }


    val addProfile by viewModel.addProfileResult.collectAsState()
    if (addProfile.success) {
        showSnack("Profile added!")
        navController.popBackStack()
        viewModel.addProfileReset()
    } else if (addProfile.error.isNotEmpty()) {
        showSnack(addProfile.error)
        viewModel.addProfileReset()
    }

    val updateProfile by viewModel.updateProfileResult.collectAsState()
    if (updateProfile.success) {
        showSnack("Profile updated!")
        navController.popBackStack()
        viewModel.updateProfileReset()
    } else if (updateProfile.error.isNotEmpty()) {
        showSnack(updateProfile.error)
        viewModel.updateProfileReset()
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
            Card(modifier = Modifier.align(Alignment.Center)) {
                Column(modifier = Modifier.padding(10.dp)) {
                    TextField(value = title,
                        placeholder = { Text(text = "Enter profile title") },
                        onValueChange = { title = it})
                    TextField(value = name,
                        placeholder = { Text(text = "Enter name") },
                        onValueChange = { name = it})
                    TextField(value = designation,
                        placeholder = { Text(text = "Enter designation") },
                        onValueChange = { designation = it})
                    TextField(value = email,
                        placeholder = { Text(text = "Enter email") },
                        onValueChange = { email = it})
                    TextField(value = phone,
                        placeholder = { Text(text = "Enter phone") },
                        onValueChange = { phone = it})
                    TextField(value = address,
                        placeholder = { Text(text = "Enter address") },
                        onValueChange = { address = it})
                    TextField(value = nationality,
                        placeholder = { Text(text = "Enter nationality") },
                        onValueChange = { nationality = it})
                    TextField(value = description,
                        placeholder = { Text(text = "Enter description") },
                        onValueChange = { description = it })
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
}