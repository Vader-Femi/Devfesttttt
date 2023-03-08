package com.example.phone

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.phone.ui.theme.DevfestttttTheme
import com.google.android.gms.wearable.Asset
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.Wearable
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream

class MainActivity : ComponentActivity() {

    private val dataClient by lazy { Wearable.getDataClient(this) }
    private val messageClient by lazy { Wearable.getMessageClient(this) }
    private val capabilityClient by lazy { Wearable.getCapabilityClient(this) }

    private val isCameraSupported by lazy {
        packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)
    }

    private val clientDataViewModel by viewModels<ClientDataViewModel>()

    private val takePhotoLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        clientDataViewModel.onPictureTaken(bitmap = bitmap)
    }


    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DevfestttttTheme {
                Scaffold(
                    content = {
                        Box(
                            modifier = Modifier.padding(it),
                            contentAlignment = Alignment.Center
                        ){
                            MainApp(
                                image = clientDataViewModel.image,
                                isCameraSupported = isCameraSupported,
                                onTakePhotoClick = ::takePhoto,
                                onSendPhotoClick = ::sendPhoto,
                                onSendEmail = ::sendEmailAddress,
                                onStartWearableActivityClick = ::startWearableActivity
                            )
                        }

                    }
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        dataClient.addListener(clientDataViewModel)
        messageClient.addListener(clientDataViewModel)
        capabilityClient.addListener(
            clientDataViewModel,
            Uri.parse("wear://"),
            CapabilityClient.FILTER_REACHABLE
        )
    }

    override fun onPause() {
        super.onPause()
        dataClient.removeListener(clientDataViewModel)
        messageClient.removeListener(clientDataViewModel)
        capabilityClient.removeListener(clientDataViewModel)
    }

    private fun startWearableActivity() {
        lifecycleScope.launch {
            try {
                val nodes = capabilityClient
                    .getCapability(WEAR_CAPABILITY, CapabilityClient.FILTER_REACHABLE)
                    .await()
                    .nodes

                // Send a message to all nodes in parallel
                nodes.map { node ->
                    async {
                        messageClient.sendMessage(node.id, START_ACTIVITY_PATH, byteArrayOf())
                            .await()
                    }
                }.awaitAll()

            } catch (cancellationException: CancellationException) {
                throw cancellationException
            } catch (exception: Exception) {
                Log.d(TAG, "Starting activity failed: $exception")
            }
        }
    }

    private fun takePhoto() {
        if (!isCameraSupported) return
        takePhotoLauncher.launch(null)
    }

    private fun sendPhoto() {
        lifecycleScope.launch {
            try {
                val image = clientDataViewModel.image ?: return@launch
                val imageAsset = image.toAsset()
                val request = PutDataMapRequest.create(IMAGE_PATH).apply {
                    dataMap.putAsset(IMAGE_KEY, imageAsset)
                }
                    .asPutDataRequest()
                    .setUrgent()

                val result = dataClient.putDataItem(request).await()

                Log.d(TAG, "Photo Sending: $result")
            } catch (cancellationException: CancellationException) {
                throw cancellationException
            } catch (exception: Exception) {
                Log.d(TAG, "Photo Sending Failed: $exception")
            }
        }
    }

    private suspend fun sendEmailAddress(email: String) {
            try {
                val request = PutDataMapRequest.create(EMAIL_PATH).apply {
                    dataMap.putString(EMAIL_KEY, email)
                }
                    .asPutDataRequest()
                    .setUrgent()

                val result = dataClient.putDataItem(request).await()

                Log.d(TAG, "Email Sending: $result")
            } catch (cancellationException: CancellationException) {
                Log.d(TAG, "Email Sending: $cancellationException")
                throw cancellationException
            } catch (exception: Exception) {
                Log.d(TAG, "Email Sending Failed: $exception")
            }
    }


    private suspend fun Bitmap.toAsset(): Asset =
        withContext(Dispatchers.Default) {
            ByteArrayOutputStream().use { byteStream ->
                compress(Bitmap.CompressFormat.PNG, 100, byteStream)
                Asset.createFromBytes(byteStream.toByteArray())
            }
        }

    companion object {
        private const val TAG = "MainActivityPhone"

        private const val START_ACTIVITY_PATH = "/start-activity"
        private const val IMAGE_PATH = "/image"
        private const val EMAIL_PATH = "/email"
        private const val IMAGE_KEY = "photo"
        private const val EMAIL_KEY = "email"
        private const val WEAR_CAPABILITY = "wear"
    }

}