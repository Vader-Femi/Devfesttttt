package com.example.devfesttttt.presentation.authentication.navigation

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavHostState
import com.example.devfesttttt.presentation.Screen
import com.example.devfesttttt.presentation.authentication.ClientDataViewModel
import com.example.devfesttttt.presentation.authentication.Event
import com.example.devfesttttt.presentation.authentication.ui.MainApp
import com.example.devfesttttt.presentation.authentication.ui.onboarding.OnBoardingScreen
import com.example.devfesttttt.presentation.authentication.ui.signin.SignInConfirmationScreen
import com.example.devfesttttt.presentation.authentication.ui.signin.SignInScreen
import com.example.devfesttttt.presentation.authentication.viewmodel.AuthenticationViewModel
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalPagerApi
@Composable
fun AuthenticationNavigation(
    navController: NavHostController,
    viewModel: AuthenticationViewModel,
    events: List<Event>,
    image: Bitmap?,
    onQueryOtherDevicesClicked: () -> Unit,
    onQueryMobileCameraClicked: () -> Unit
) {

    SwipeDismissableNavHost(
        navController = navController,
        startDestination = Screen.OnBoardingScreen.route,
        route = Screen.AuthenticationRoute.route,
        state = rememberSwipeDismissableNavHostState()
    ) {

        composable(route = Screen.OnBoardingScreen.route) {
            OnBoardingScreen(navController = navController, viewModel = viewModel)
        }

        composable(route = Screen.SignInConfirmationScreen.route) {
            SignInConfirmationScreen(navController = navController, viewModel = viewModel)
        }

        composable(route = Screen.SignInScreen.route) {
//            SignInScreen(
            MainApp(
//                navController = navController,
//                viewModel = viewModel,
                events = events,
                image = image,
                onQueryOtherDevicesClicked = onQueryOtherDevicesClicked,
                onQueryMobileCameraClicked = onQueryMobileCameraClicked
            )
        }

    }
}