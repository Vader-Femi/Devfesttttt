package com.example.devfesttttt.presentation

sealed class Screen(val route: String) {
    object AuthenticationRoute : Screen("authentication_route")
    object OnBoardingScreen : Screen("on_boarding_screen")

    fun withArgs(vararg args: Pair<String, String>): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("?${arg.first}=${arg.second}")
            }
        }
    }
}
