package com.example.devfesttttt.presentation.authentication.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.example.devfesttttt.presentation.authentication.navigation.AuthenticationNavigation
import com.example.devfesttttt.presentation.authentication.viewmodel.AuthenticationViewModel
import com.example.devfesttttt.presentation.theme.DevfestttttTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.Wearable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await
import java.time.Instant
import javax.inject.Inject

@AndroidEntryPoint
@ExperimentalPagerApi
class AuthenticationActivity : ComponentActivity(){

    private val dataClient by lazy { Wearable.getDataClient(this) }

    @Inject
    lateinit var viewModel: AuthenticationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberSwipeDismissableNavController()
            DevfestttttTheme {
                Scaffold(
                    content = {
                        Box {
                            AuthenticationNavigation(
                                navController = navController,
                                viewModel = viewModel,
                                profileImage = viewModel.image,
                                profileEmail = viewModel.email,
                                profileName = viewModel.name,
                                onSendPairingStatus = ::sendPairingStatus
                            )
                        }
                    }
                )
            }
        }
    }

    private suspend fun sendPairingStatus(pairingComplete: Boolean) {
        try {
            val request = PutDataMapRequest.create(PAIRING_COMPLETE_PATH).apply {
                dataMap.putBoolean(PAIRING_COMPLETE_KEY, pairingComplete)
                dataMap.putLong(TIME_KEY, Instant.now().epochSecond)
            }
                .asPutDataRequest()
                .setUrgent()

            val result = dataClient.putDataItem(request).await()

            Log.d(TAG, "Pairing Status: $result")
        } catch (cancellationException: CancellationException) {
            Log.d(TAG, "Pairing Status: $cancellationException")
            throw cancellationException
        } catch (exception: Exception) {
            Log.d(TAG, "Pairing Failed: $exception")
        }
    }

    override fun onResume() {
        super.onResume()
        dataClient.addListener(viewModel)
    }

    override fun onPause() {
        super.onPause()
        dataClient.removeListener(viewModel)
    }

    companion object {
        private const val TAG = "MainActivityPhone"
        private const val PAIRING_COMPLETE_PATH = "/pairing-complete"
        private const val PAIRING_COMPLETE_KEY = "pairing-complete"
        private const val TIME_KEY = "time"
    }
}