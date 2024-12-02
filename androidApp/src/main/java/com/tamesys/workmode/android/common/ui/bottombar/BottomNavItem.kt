package com.tamesys.workmode.android.common.ui.bottombar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.tamesys.workmode.android.home.Screen

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    data object Profiles : BottomNavItem(Screen.Profiles.route, Icons.Default.Home, "Profiles")
    data object Jobs : BottomNavItem(Screen.Jobs.route, Icons.Default.Search, "Jobs")
    data object Settings : BottomNavItem(Screen.Settings.route, Icons.Default.Settings, "Settings")
}