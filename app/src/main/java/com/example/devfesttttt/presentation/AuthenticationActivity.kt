package com.example.devfesttttt.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Surface
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.example.devfesttttt.presentation.theme.DevfestttttTheme
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalPagerApi
class AuthenticationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberSwipeDismissableNavController()
            DevfestttttTheme {
                Scaffold(
                    content = {
                        Box {
                            AuthenticationNavigation(
                                navController = navController
                            )
                        }
                    }
                )
            }
        }
    }
}