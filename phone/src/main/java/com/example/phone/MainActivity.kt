package com.example.phone

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
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
import java.time.Instant

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
                        LaunchedEffect(key1 = clientDataViewModel.pairingComplete ){
                            if (clientDataViewModel.pairingComplete)
                                Toast.makeText(
                                    this@MainActivity,
                                    "Pairing Complete",
                                    Toast.LENGTH_SHORT
                                ).show()
                        }
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
                                onSendName = ::sendName,
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
    }

    override fun onPause() {
        super.onPause()
        dataClient.removeListener(clientDataViewModel)
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

                Log.d(TAG, "Starting activity : $nodes")
            } catch (cancellationException: CancellationException) {
                Log.d(TAG, "Starting activity: $cancellationException")
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
                    dataMap.putLong(TIME_KEY, Instant.now().epochSecond)
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
                    dataMap.putLong(TIME_KEY, Instant.now().epochSecond)
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

    private suspend fun sendName(name: String) {
            try {
                val request = PutDataMapRequest.create(NAME_PATH).apply {
                    dataMap.putString(NAME_KEY, name)
                    dataMap.putLong(TIME_KEY, Instant.now().epochSecond)
                }
                    .asPutDataRequest()
                    .setUrgent()

                val result = dataClient.putDataItem(request).await()

                Log.d(TAG, "Name Sending: $result")
            } catch (cancellationException: CancellationException) {
                Log.d(TAG, "Name Sending: $cancellationException")
                throw cancellationException
            } catch (exception: Exception) {
                Log.d(TAG, "Name Sending Failed: $exception")
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
        private const val NAME_PATH = "/name"
        const val PAIRING_COMPLETE_PATH = "/pairing-complete"
        private const val IMAGE_KEY = "photo"
        private const val EMAIL_KEY = "email"
        private const val NAME_KEY = "name"
        const val PAIRING_COMPLETE_KEY = "pairing-complete"
        private const val TIME_KEY = "time"
        private const val WEAR_CAPABILITY = "wear"
    }

}