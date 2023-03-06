package com.example.devfesttttt.presentation

sealed class Screen(val route: String) {
    object AuthenticationRoute : Screen("authentication_route")
    object DevFestRoute : Screen("dev_fest_route")
    object OnBoardingScreen : Screen("on_boarding_screen")
    object EventOptionsScreen : Screen("event_options_screen")
    object AgendaListScreen : Screen("agenda_list_screen")
    object AgendaDetailsScreen : Screen("agenda_details_screen")
    object SessionListScreen : Screen("session_list_screen")
    object SessionDetailsScreen : Screen("session_details_screen")
    object SpeakerListScreen : Screen("speaker_list_screen")
    object SpeakerDetailsScreen : Screen("speaker_details_screen")

    fun withIdArg(arg: Pair<String, Int>): String {
        return buildString {
            append(route)
            append("?${arg.first}=${arg.second}")
        }
    }
}
