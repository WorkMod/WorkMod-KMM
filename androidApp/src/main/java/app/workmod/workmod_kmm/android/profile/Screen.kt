package app.workmod.workmod_kmm.android.profile

sealed class Screen(val route: String) {
    data object AllProfiles: Destination("profile-all")
    data object ProfileDetails: Destination("profile-details?id={id}")
    data object AddProfile: Destination("profile-add?id={id}")
}