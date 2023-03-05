package com.example.devfesttttt.presentation.authentication.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.example.devfesttttt.presentation.authentication.navigation.AuthenticationNavigation
import com.example.devfesttttt.presentation.authentication.viewmodel.AuthenticationViewModel
import com.example.devfesttttt.presentation.theme.DevfestttttTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalPagerApi
class AuthenticationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberSwipeDismissableNavController()
            val viewModel = hiltViewModel<AuthenticationViewModel>()
            DevfestttttTheme {
                Scaffold(
                    content = {
                        Box {
                            AuthenticationNavigation(
                                navController = navController,
                                viewModel = viewModel
                            )
                        }
                    }
                )
            }
        }
    }
}