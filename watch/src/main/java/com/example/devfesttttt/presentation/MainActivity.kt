package com.example.devfesttttt.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.devfesttttt.presentation.authentication.ui.AuthenticationActivity
import com.example.devfesttttt.presentation.devfest.ui.DevFestActivity
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
@ExperimentalPagerApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = hiltViewModel<BaseViewModel>()
            val coroutineScope = rememberCoroutineScope()
            LaunchedEffect(key1 = true) {
                coroutineScope.launch {
                    if (viewModel.userSignedIn())
                        jumpToDevFestActivity()
                    else
                        goToAuthenticationActivity()
                }
            }

        }
    }

    private fun goToAuthenticationActivity() {
        Intent(this, AuthenticationActivity::class.java).also {
            it.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
            finish()
        }
    }

    private fun jumpToDevFestActivity() {
        Intent(this, DevFestActivity::class.java).also { intent ->
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
