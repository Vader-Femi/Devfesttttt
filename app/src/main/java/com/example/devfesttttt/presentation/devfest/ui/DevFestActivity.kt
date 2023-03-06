package com.example.devfesttttt.presentation.devfest.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.example.devfesttttt.presentation.devfest.navigation.DevFestNavigation
import com.example.devfesttttt.presentation.devfest.viewmodel.DevFestViewModel
import com.example.devfesttttt.presentation.theme.DevfestttttTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DevFestActivity : ComponentActivity() {
    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberSwipeDismissableNavController()
            val viewModel = hiltViewModel<DevFestViewModel>()
            DevfestttttTheme {
                DevFestNavigation(
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }
    }
}