package com.tamesys.workmode.android.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tamesys.workmode.profile.domain.model.Education
import com.tamesys.workmode.profile.domain.model.Employment
import com.tamesys.workmode.profile.domain.model.SkillSet

@Composable
internal fun EmploymentDetails(employments: List<Employment>, onDelete: ((index: Int) -> Unit)? = null) {
    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()) {
        Text(
            modifier = Modifier.padding(6.dp),
            text = "Employments",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            //textDecoration = TextDecoration.Underline
        )
        Card(colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        )) {
            Column(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()) {
                if (employments.isEmpty()) {
                    Text("No Employments added!")
                } else {
                    for (i in employments.indices) {
                        EmploymentItem(employments[i], i, onDelete)
                        if (i < employments.size - 1) {
                            Divider(modifier = Modifier.height(6.dp).padding(vertical = 2.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EmploymentItem(employment: Employment, index: Int, onDelete: ((deleteIndex: Int) -> Unit)?) {
    Row (modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
        Column {
            Text(
                text = employment.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
            Text(
                text = employment.company,
                fontSize = 16.sp,
                color = Color.DarkGray
            )
            Text(
                text = employment.from + " - " + employment.to,
                fontSize = 14.sp,
                color = Color(1, 53, 8, 255)
            )
        }
        if (onDelete != null) {
            IconButton(
                onClick = {
                    onDelete(index)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = Color.Red
                )
            }
        }
    }
}

@Composable
internal fun EducationDetails(educations: List<Education>, onDelete: ((index: Int) -> Unit)? = null) {
    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()) {
        Text(modifier = Modifier.padding(6.dp),
            text = "Educations",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            //textDecoration = TextDecoration.Underline
        )
        Card(colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        )) {
            Column(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()) {
                if (educations.isEmpty()) {
                    Text("No Educations added!")
                } else {
                    for (i in educations.indices) {
                        EducationItem(education = educations[i], i, onDelete)
                        if (i < educations.size - 1) {
                            Divider(modifier = Modifier.height(6.dp).padding(vertical = 2.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EducationItem(education: Education, index: Int, onDelete: ((deleteIndex: Int) -> Unit)?) {
    Row (modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
        Column {
            Text(text = education.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black)
            Text(text = education.school,
                fontSize = 16.sp,
                color = Color.DarkGray)
            Text(text = education.from + " - " + education.to,
                fontSize = 14.sp,
                color = Color(1, 53, 8, 255))
        }
        if (onDelete != null) {
            IconButton(
                onClick = {
                    onDelete(index)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = Color.Red
                )
            }
        }

    }
}

@Composable
internal fun SkillSetDetails(skillSets: List<SkillSet>, onDelete: ((index: Int) -> Unit)? = null) {
    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()) {
        Text(modifier = Modifier.padding(6.dp),
            text = "SkillSets",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            //textDecoration = TextDecoration.Underline
        )
        Card(colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        )) {
            Column(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()) {
                if (skillSets.isEmpty()) {
                    Text("No SkillSets added!")
                } else {
                    for (i in skillSets.indices) {
                        SkillSetItem(skillSet = skillSets[i], i, onDelete)
                        if (i < skillSets.size - 1) {
                            Divider(modifier = Modifier.height(6.dp).padding(vertical = 2.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SkillSetItem(skillSet: SkillSet, index: Int, onDelete: ((deleteIndex: Int) -> Unit)?) {
    Row (modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
        Column {
            Text(text = skillSet.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black)
            Text(text = skillSet.description,
                fontSize = 16.sp,
                color = Color.DarkGray)
            Text(text = skillSet.skills.joinToString(),
                fontSize = 14.sp,
                color = Color(1, 53, 8, 255))
        }
        if (onDelete != null) {
            IconButton(
                onClick = {
                    onDelete(index)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = Color.Red
                )
            }
        }

    }
}