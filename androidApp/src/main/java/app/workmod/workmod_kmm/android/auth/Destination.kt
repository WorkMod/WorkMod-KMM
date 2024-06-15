package app.workmod.workmod_kmm.android.auth

sealed class Destination(val route: String) {
    data object SignIn: Destination("signIn")
    data object SignUp: Destination("signUp")
}
