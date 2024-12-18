package com.tamesys.workmode.android.profile.add

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.tamesys.workmode.profile.domain.model.Education
import com.tamesys.workmode.profile.presentation.ProfileViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEducation(
    navController: NavHostController,
    showSnack: (message: String) -> Unit,
    viewModel: ProfileViewModel = koinViewModel(),
    education: Education? = null
) {
    //onEducationAdded: (education: Education) -> Unit) {
    var educationTitle by remember { mutableStateOf("") }
    var educationSchool by remember { mutableStateOf("") }
    var educationFrom by remember { mutableStateOf("") }
    var educationTo by remember { mutableStateOf("") }
    Scaffold(modifier = Modifier.padding(bottom = 0.dp),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Add Education")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                })
        }
    ) { padding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(padding)) {
            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    TextField(value = educationTitle,
                        label = { Text(text = "Title") },
                        onValueChange = { educationTitle = it })
                    TextField(value = educationSchool,
                        label = { Text(text = "School") },
                        onValueChange = { educationSchool = it })
                    TextField(value = educationFrom,
                        label = { Text(text = "From") },
                        onValueChange = { educationFrom = it })
                    TextField(value = educationTo,
                        label = { Text(text = "To") },
                        onValueChange = { educationTo = it })
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(modifier = Modifier, onClick = {
                        if (educationTitle.isBlank()
                            || educationSchool.isBlank()
                            || educationFrom.isBlank()
                            || educationTo.isBlank()
                        ) {
                            showSnack.invoke("Enter all education details!")
                            return@Button
                        }

                        viewModel.addEducation(Education(
                            id = "", title = educationTitle, school = educationSchool,
                            from = educationFrom, to = educationTo
                        )
                        )
                        navController.navigateUp()
                        educationTitle = ""
                        educationSchool = ""
                        educationFrom = ""
                        educationTo = ""
                    }) {
                        Text("Add Education")
                    }
                }
            }
        }
    }
}