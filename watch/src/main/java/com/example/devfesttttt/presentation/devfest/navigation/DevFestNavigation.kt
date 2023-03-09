package com.example.devfesttttt.presentation.devfest.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.wear.compose.navigation.composable
import androidx.navigation.navArgument
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.rememberSwipeDismissableNavHostState
import com.example.devfesttttt.presentation.Screen
import com.example.devfesttttt.presentation.devfest.ui.EventOptionsScreen
import com.example.devfesttttt.presentation.devfest.ui.agenda.AgendaDetailsScreen
import com.example.devfesttttt.presentation.devfest.ui.agenda.AgendaListScreen
import com.example.devfesttttt.presentation.devfest.ui.profile.ProfileScreen
import com.example.devfesttttt.presentation.devfest.ui.session.SessionDetailsScreen
import com.example.devfesttttt.presentation.devfest.ui.session.SessionListScreen
import com.example.devfesttttt.presentation.devfest.ui.speaker.SpeakerDetailsScreen
import com.example.devfesttttt.presentation.devfest.ui.speaker.SpeakerListScreen
import com.example.devfesttttt.presentation.devfest.viewmodel.DevFestViewModel
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalPagerApi
@Composable
fun DevFestNavigation(
    navController: NavHostController,
    viewModel: DevFestViewModel
) {

    SwipeDismissableNavHost(
        navController = navController,
        startDestination = Screen.EventOptionsScreen.route,
        route = Screen.DevFestRoute.route,
        state = rememberSwipeDismissableNavHostState()
    ) {

        composable(route = Screen.EventOptionsScreen.route) {
            EventOptionsScreen(navController = navController)
        }

        composable(route = Screen.AgendaListScreen.route) {
            AgendaListScreen(navController = navController, viewModel = viewModel)
        }

        composable(route = Screen.AgendaDetailsScreen.route+ "?agenda_id={agenda_id}",
            listOf(
                navArgument("agenda_id") {
                    type = NavType.IntType
                    nullable = false
                },
            )
        ) { entry ->
            AgendaDetailsScreen(
                navController = navController,
                viewModel = viewModel,
                agenda_id = entry.arguments?.getInt("agenda_id") ?: 0
            )
        }

        composable(route = Screen.SessionListScreen.route) {
            SessionListScreen(navController = navController, viewModel = viewModel)
        }

        composable(route = Screen.SessionDetailsScreen.route+ "?session_id={session_id}",
            listOf(
                navArgument("session_id") {
                    type = NavType.IntType
                    nullable = false
                },
            )
        ) { entry ->
            SessionDetailsScreen(
                navController = navController,
                viewModel = viewModel,
                session_id = entry.arguments?.getInt("session_id") ?: 0
            )
        }

        composable(route = Screen.SpeakerListScreen.route) {
            SpeakerListScreen(navController = navController, viewModel = viewModel)
        }

        composable(route = Screen.SpeakerDetailsScreen.route+ "?speaker_id={speaker_id}",
            listOf(
                navArgument("speaker_id") {
                    type = NavType.IntType
                    nullable = false
                },
            )
        ) { entry ->
            SpeakerDetailsScreen(
                navController = navController,
                viewModel = viewModel,
                speaker_id = entry.arguments?.getInt("speaker_id") ?: 0
            )
        }

        composable(route = Screen.ProfileScreen.route) {
            ProfileScreen(navController = navController, viewModel = viewModel)
        }

    }
}