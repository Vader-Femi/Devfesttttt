package com.example.devfesttttt.presentation

sealed class Screen(val route: String) {
    object AuthenticationRoute : Screen("authentication_route")
    object AgendaRoute : Screen("agenda_route")
    object OnBoardingScreen : Screen("on_boarding_screen")
    object AgendaListScreen : Screen("agenda_list_screen")
    object AgendaDetailsScreen : Screen("agenda_details_screen")

    fun withIdArg(arg: Pair<String, Int>): String {
        return buildString {
            append(route)
            append("?${arg.first}=${arg.second}")
        }
    }
}
