package com.example.devfesttttt.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.wear.compose.material.Text
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalPagerApi
@Composable
fun AuthenticationNavigation(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = Screen.OnBoardingScreen.route,
        route = Screen.AuthenticationRoute.route
    ) {

        composable(route = Screen.OnBoardingScreen.route) {
            OnBoardingScreen(navController = navController)
        }

    }
}