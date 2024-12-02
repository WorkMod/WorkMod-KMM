package com.tamesys.workmode.android.profile

sealed class Destination(val route: String) {
    data object ProfileDetails: Destination("profile-details?id={id}")
    data object AddProfile: Destination("profile-add?id={id}")
}