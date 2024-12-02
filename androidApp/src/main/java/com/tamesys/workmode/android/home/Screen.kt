package com.tamesys.workmode.android.home

sealed class Screen(val route: String) {
    data object Profiles: Screen("profile-all")
    data object ProfileDetails: Screen("profile-details?id={id}")
    data object ProfileAdd: Screen("profile-add?id={id}")
    data object Jobs: Screen("jobs")
    data object Settings: Screen("settings")
}