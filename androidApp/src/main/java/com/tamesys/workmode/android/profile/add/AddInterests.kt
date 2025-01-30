package com.tamesys.workmode.android.profile.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ContextualFlowRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.tamesys.workmode.profile.presentation.ProfileViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddInterests(
    navController: NavHostController,
    showSnack: (message: String) -> Unit,
    viewModel: ProfileViewModel = koinViewModel(),
) {

    var input by remember {  mutableStateOf("") }
    val interestList = remember { mutableStateListOf<String>() }
    LaunchedEffect(Unit) {
        interestList.addAll(viewModel.interests.value)
    }

    //var interestList by remember { mutableStateOf(listOf<String>()) }

    Box() {
        Column {
            Row(modifier = Modifier.padding(8.dp)) {
                TextField(modifier = Modifier.weight(1.0F),
                    value = input,
                    label = { Text("Interest")},
                    onValueChange = { input = it })
                Button(modifier = Modifier.padding(start = 8.dp)
                    .align(Alignment.CenterVertically),
                    onClick = {
                        if (input.isNotEmpty()) {
                            interestList.add(input)
                            input = ""
                        } else {
                            showSnack("Enter your interest!")
                        }
                }) { Text("Add") }
            }
            ContextualFlowRow(
                modifier = Modifier
                    .safeDrawingPadding()
                    .fillMaxWidth(1f)
                    .fillMaxHeight()
                    .weight(1.0F)
                    .padding(16.dp, 8.dp)
                    .wrapContentHeight(align = Alignment.Top)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                itemCount = interestList.size
            ) { index ->
                if (index < interestList.size) { // TODO this check is not needed normally
                    ChipItem(interestList[index],
                        trailingIcon = { Icon(Icons.Default.Clear, "Back")}
                    ) {
                        interestList.removeAt(index)
                    }
                }
            }
            Button(modifier = Modifier.padding(8.dp)
                .fillMaxWidth(),
                onClick = {
                    viewModel.setInterests(interestList)
                    navController.navigateUp()
                }) { Text("Update Interests") }
        }
    }

}