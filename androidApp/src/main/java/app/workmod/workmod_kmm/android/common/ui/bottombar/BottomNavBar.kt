package com.rahbertheadvisor.android.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import app.workmod.workmod_kmm.android.common.ui.bottombar.BottomNavItem

@Composable
fun BottomNavigationBar(navController: NavController, visibility: State<Boolean>) {
    AnimatedVisibility(
        visible = visibility.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            BottomNavigation(
                modifier = Modifier.background(color = Color.White)
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val items = listOf(
                    BottomNavItem.Profiles,
                    BottomNavItem.Jobs,
                    BottomNavItem.Settings,
                )

                items.forEach { item ->
                    val isSelected = currentRoute == item.route
                    val bgColor = if (isSelected) Color(0xFF89C9A4)//MaterialTheme.colorScheme.primary
                    else Color.White
                    BottomNavigationItem(
                        modifier = Modifier.background(color = bgColor),
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        },
                        icon = { Icon(item.icon, contentDescription = null) },
                        label = { Text(item.label) }

                    )
                }
            }
        }
    )
}