package com.keepqueue.workmode.common.ui.bottombar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    data object Profile : BottomNavItem("profile-all", Icons.Default.Home, "Profile")
    data object Jobs : BottomNavItem("jobs", Icons.Default.Search, "Jobs")
    data object Settings : BottomNavItem("settings", Icons.Default.Settings, "Settings")
}