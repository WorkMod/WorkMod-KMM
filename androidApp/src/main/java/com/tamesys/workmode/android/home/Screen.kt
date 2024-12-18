package com.tamesys.workmode.android.home

sealed class Screen(val route: String) {
    data object Profiles: Screen("profile-all")
    data object ProfileDetails: Screen("profile-details?id={id}")
    data object ProfileAdd: Screen("profile-add?id={id}")
    data object ProfileAddEmployment: Screen("profile-add-employment")
    data object ProfileAddEducation: Screen("profile-add-education")
    data object Jobs: Screen("jobs")
    data object Settings: Screen("settings")
}