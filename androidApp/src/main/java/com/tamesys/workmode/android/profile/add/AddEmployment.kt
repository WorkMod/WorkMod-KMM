package com.tamesys.workmode.android.profile.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import com.tamesys.workmode.android.profile.Destination
import com.tamesys.workmode.profile.domain.model.Employment
import com.tamesys.workmode.profile.presentation.ProfileViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEmployment(navController: NavHostController,
                  showSnack: (message: String) -> Unit,
                  viewModel: ProfileViewModel = koinViewModel(),
                  employment: Employment? = null) {
                  //onAddEmployment: (employment: Employment) -> Unit) {
    var employmentTitle by remember { mutableStateOf(employment?.title ?: "") }
    var employmentCompany by remember { mutableStateOf(employment?.company ?: "") }
    var employmentFrom by remember { mutableStateOf(employment?.from ?: "") }
    var employmentTo by remember { mutableStateOf(employment?.to ?: "") }
    Scaffold(modifier = Modifier.padding(bottom = 0.dp),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Add Employment")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack,"Back")
                    }
                })
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            Card(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(10.dp, 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(value = employmentTitle,
                        label = { Text(text = "Title") },
                        onValueChange = { employmentTitle = it })
                    TextField(value = employmentCompany,
                        label = { Text(text = "Company") },
                        onValueChange = { employmentCompany = it })
                    TextField(value = employmentFrom,
                        label = { Text(text = "From") },
                        onValueChange = { employmentFrom = it })
                    TextField(value = employmentTo,
                        label = { Text(text = "To") },
                        onValueChange = { employmentTo = it })
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(modifier = Modifier, onClick = {
                        if (employmentTitle.isBlank()
                            || employmentCompany.isBlank()
                            || employmentFrom.isBlank()
                            || employmentTo.isBlank()) {
                            showSnack.invoke("Enter all employment details!")
                            return@Button
                        }

                        /*onAddEmployment(Employment(
                            id = "", title = employmentTitle, company = employmentCompany,
                            from = employmentFrom, to = employmentTo
                        ))*/
                        viewModel.addEmployment(Employment(
                            id = "", title = employmentTitle, company = employmentCompany,
                            from = employmentFrom, to = employmentTo
                        ))
                        navController.navigateUp()
                        employmentTitle = ""
                        employmentCompany = ""
                        employmentFrom = ""
                        employmentTo = ""
                    }) {
                        Text("Add Employment")
                    }
                }
            }
        }
    }


}