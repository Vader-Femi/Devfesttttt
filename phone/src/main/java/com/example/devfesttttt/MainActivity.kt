package com.example.devfesttttt

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.devfesttttt.ui.theme.DevfestttttTheme
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.wearable.Asset
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.Wearable
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.net.URL
import java.time.Instant


class MainActivity : ComponentActivity() {

    private val dataClient by lazy { Wearable.getDataClient(this) }
    private val messageClient by lazy { Wearable.getMessageClient(this) }
    private val capabilityClient by lazy { Wearable.getCapabilityClient(this) }

    private val viewModel by viewModels<MainViewModel>()

    private val signInLauncher = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            try {
                if (result.resultCode == RESULT_OK) {
                    val oneTapClient = Identity.getSignInClient(this)
                    val credentials =
                        oneTapClient.getSignInCredentialFromIntent(result.data)
                    if (credentials.profilePictureUri != null) {
                        Toast.makeText(this, "Please wait for your image to download before sending", Toast.LENGTH_LONG).show()
                        lifecycleScope.launch (Dispatchers.IO) {
                            try {
                                val url =
                                    URL(credentials.profilePictureUri.toString())
                                val image = BitmapFactory.decodeStream(
                                    url.openConnection().getInputStream()
                                )
                                viewModel.onPictureTaken(image)
                            } catch (e: Exception) {
                                Toast.makeText(this@MainActivity, "Cannot download your profile image, so settle for that", Toast.LENGTH_LONG).show()
                                Log.e(TAG, "Cannot download image: $e")
                                viewModel.onPictureTaken(BitmapFactory.decodeResource(resources, R.drawable.avataaars))
                            }
                        }
                    } else {
                        BitmapFactory.decodeResource(resources, R.drawable.avataaars)
                    }
                    viewModel.saveName(credentials.givenName)
                    viewModel.setSignInStatus(true)
                    viewModel.saveEmail(credentials.id)
                } else {
                    Toast.makeText(this, "Dialog Canceled", Toast.LENGTH_SHORT).show()
                }
            } catch (e: ApiException) {
                when (e.statusCode) {
                    CommonStatusCodes.CANCELED -> {
                        Toast.makeText(this, "Dialog Canceled", Toast.LENGTH_SHORT).show()
                    }
                    CommonStatusCodes.NETWORK_ERROR -> {
                        Toast.makeText(this, "Network Error", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Toast.makeText(this, e.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }

        } else {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            Log.e(TAG, result.toString())
        }

    }

    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DevfestttttTheme {
                Scaffold(
                    content = {
                        LaunchedEffect(key1 = viewModel.pairingComplete) {
                            if (viewModel.pairingComplete)
                                Toast.makeText(
                                    this@MainActivity,
                                    "Pairing Complete",
                                    Toast.LENGTH_SHORT
                                ).show()
                        }

                        Box(
                            modifier = Modifier
                                .padding(it)
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            MyApp(
                                email = viewModel.email,
                                name = viewModel.name,
                                image = viewModel.image,
                                isSignedIn = viewModel.isSignedIn,
                                onSignIn = ::signIn,
                                onSendToWear = ::sendToWear,
                                onSignOut = ::signOut,
                                onStartAppOnWatch = ::startWearableActivity
                            )
                        }
                    }
                )
            }
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

    private fun signIn() {

        val oneTapClient = Identity.getSignInClient(this)
        oneTapClient.signOut()
        val signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(BuildConfig.WEB_CLIENT_ID)
                    .setFilterByAuthorizedAccounts(true)
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()

        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener { result ->
                try {
                    signInLauncher.launch(
                        IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                    )
                } catch (e: Exception) {
                    Toast.makeText(this, e.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Sign In: $it", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "Sign In: $it")
                signUp()
            }

    }

    private fun signUp() {
        val oneTapClient = Identity.getSignInClient(this)
        val signUpRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(BuildConfig.WEB_CLIENT_ID)
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            ).build()

        oneTapClient.beginSignIn(signUpRequest)
            .addOnSuccessListener(this) { result ->
                try {
                    signInLauncher.launch(
                        IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                    )
                } catch (e: Exception) {
                    Toast.makeText(this, e.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener(this) {
                Toast.makeText(this, "Sign Up: $it", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "Sign Up: $it")
            }
    }

    private fun signOut() {
        viewModel.setSignInStatus(false)
        val oneTapClient = Identity.getSignInClient(this)
        oneTapClient.signOut()
    }

    private fun sendToWear() {
        lifecycleScope.launch {
            sendPhoto()
            sendName()
            sendEmailAddress()
        }
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

                val numberOfNodes = nodes.size
                if (numberOfNodes == 0)
                    Toast.makeText(this@MainActivity, "You're not connected to a wear OS device", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(this@MainActivity, "Intent sent. Check your wear OS device(s)", Toast.LENGTH_SHORT).show()

                Log.d(TAG, "Starting activity : $nodes")
            } catch (cancellationException: CancellationException) {
                Log.d(TAG, "Starting activity: $cancellationException")
                throw cancellationException
            } catch (exception: Exception) {
                Log.d(TAG, "Starting activity failed: $exception")
            }
        }
    }

    private suspend fun sendPhoto() {
        try {
            if (viewModel.image == null){
                Toast.makeText(this, "Please wait for an image to load", Toast.LENGTH_SHORT).show()
                return
            }
            val image = viewModel.image!!
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

    private suspend fun sendEmailAddress() {
        if (viewModel.email != null) {
            try {
                val request = PutDataMapRequest.create(EMAIL_PATH).apply {
                    dataMap.putString(EMAIL_KEY, viewModel.email!!)
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
        } else {
            Toast.makeText(this, "Email is empty", Toast.LENGTH_SHORT).show()
        }
    }

    private suspend fun sendName() {
        if (viewModel.name != null) {
            try {
                val request = PutDataMapRequest.create(NAME_PATH).apply {
                    dataMap.putString(NAME_KEY, viewModel.name!!)
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
        } else {
            Toast.makeText(this, "Name is empty", Toast.LENGTH_SHORT).show()
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