package com.example.devfesttttt.presentation.authentication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.devfesttttt.presentation.Screen
import com.example.devfesttttt.presentation.authentication.ui.OnBoardingScreen
import com.example.devfesttttt.presentation.authentication.viewmodel.AuthenticationViewModel
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalPagerApi
@Composable
fun AuthenticationNavigation(
    navController: NavHostController,
    viewModel: AuthenticationViewModel
) {

    NavHost(
        navController = navController,
        startDestination = Screen.OnBoardingScreen.route,
        route = Screen.AuthenticationRoute.route
    ) {

        composable(route = Screen.OnBoardingScreen.route) {
            OnBoardingScreen(navController = navController, viewModel = viewModel)
        }

    }
}