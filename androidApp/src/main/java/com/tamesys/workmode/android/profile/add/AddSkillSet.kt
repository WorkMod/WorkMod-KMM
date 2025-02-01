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
import androidx.compose.foundation.layout.wrapContentWidth
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
import com.tamesys.workmode.profile.domain.model.SkillSet
import com.tamesys.workmode.profile.presentation.ProfileViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddSkillSet(
    navController: NavHostController,
    showSnack: (message: String) -> Unit,
    viewModel: ProfileViewModel = koinViewModel(),
) {

    var skillTitle by remember {  mutableStateOf("") }
    var skillDescription by remember {  mutableStateOf("") }
    var skillInput by remember {  mutableStateOf("") }
    val skillList = remember { mutableStateListOf<String>() }

    //var interestList by remember { mutableStateOf(listOf<String>()) }
    //Test adding skillset and complete it

    Box() {
        Column(modifier = Modifier.padding(8.dp)) {
            TextField(modifier = Modifier.fillMaxWidth(),
                value = skillTitle,
                label = { Text("Title")},
                onValueChange = { skillTitle = it })
            TextField(modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                value = skillDescription,
                label = { Text("Description")},
                onValueChange = { skillDescription = it })
            Row(modifier = Modifier.padding(top = 8.dp)) {
                TextField(modifier = Modifier.weight(1.0F),
                    value = skillInput,
                    label = { Text("Interest")},
                    onValueChange = { skillInput = it })
                Button(modifier = Modifier.padding(start = 8.dp).wrapContentWidth()
                    .align(Alignment.CenterVertically),
                    onClick = {
                        if (skillInput.isNotEmpty()) {
                            skillList.add(skillInput)
                            skillInput = ""
                        } else {
                            showSnack("Enter your Skill!")
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
                itemCount = skillList.size
            ) { index ->
                if (index < skillList.size) { // TODO this check is not needed normally
                    ChipItem(skillList[index],
                        trailingIcon = { Icon(Icons.Default.Clear, "Remove")}
                    ) {
                        skillList.removeAt(index)
                    }
                }
            }
            Button(modifier = Modifier.padding(8.dp)
                .fillMaxWidth(),
                onClick = {
                    viewModel.addSkillSet(
                        SkillSet(
                            id = "",
                            title = skillTitle,
                            skills = skillList,
                            description = skillDescription
                        ))
                    navController.navigateUp()
                }) { Text("Add SkillSet") }
        }
    }

}