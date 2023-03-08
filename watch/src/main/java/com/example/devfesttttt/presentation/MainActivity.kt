package com.example.devfesttttt.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.devfesttttt.R
import com.example.devfesttttt.presentation.authentication.ClientDataViewModel
import com.example.devfesttttt.presentation.authentication.ui.AuthenticationActivity
import com.example.devfesttttt.presentation.authentication.ui.MainApp
import com.example.devfesttttt.presentation.devfest.ui.DevFestActivity
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.Node
import com.google.android.gms.wearable.Wearable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@AndroidEntryPoint
@ExperimentalPagerApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            goToAuthenticationActivity()
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
