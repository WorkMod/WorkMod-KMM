package com.tamesys.workmode.android.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.tamesys.workmode.android.home.Screen
import com.tamesys.workmode.profile.domain.model.Profile

@Composable
fun ProfileItem(profile: Profile, navController: NavHostController) {
    Box(
        Modifier.clickable {
            navController.navigate(Screen.ProfileDetails.route.replace(
                oldValue = "{id}",
                newValue = profile.id
            ))
        }.fillMaxWidth().padding(15.dp)
    ) {
        Text(profile.title)
    }
}