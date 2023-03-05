package com.example.devfesttttt.presentation.devfest.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.devfesttttt.presentation.Screen
import com.example.devfesttttt.presentation.devfest.ui.AgendaDetailsScreen
import com.example.devfesttttt.presentation.devfest.ui.AgendaListScreen
import com.example.devfesttttt.presentation.devfest.viewmodel.DevFestViewModel
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalPagerApi
@Composable
fun DevFestNavigation(
    navController: NavHostController,
    viewModel: DevFestViewModel
) {

    NavHost(
        navController = navController,
        startDestination = Screen.AgendaListScreen.route,
        route = Screen.AgendaRoute.route
    ) {

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

    }
}