package com.example.devfesttttt.presentation.authentication.ui

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.example.devfesttttt.R
import com.example.devfesttttt.presentation.authentication.ClientDataViewModel
import com.example.devfesttttt.presentation.authentication.navigation.AuthenticationNavigation
import com.example.devfesttttt.presentation.authentication.viewmodel.AuthenticationViewModel
import com.example.devfesttttt.presentation.theme.DevfestttttTheme
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
class AuthenticationActivity : ComponentActivity(){

    private val dataClient by lazy { Wearable.getDataClient(this) }
    private val messageClient by lazy { Wearable.getMessageClient(this) }
    private val capabilityClient by lazy { Wearable.getCapabilityClient(this) }

    private val clientDataViewModel by viewModels<ClientDataViewModel>()
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
                                viewModel = viewModel,
                                events = clientDataViewModel.events,
                                image = clientDataViewModel.image,
                                onQueryOtherDevicesClicked = ::onQueryOtherDevicesClicked,
                                onQueryMobileCameraClicked = ::onQueryMobileCameraClicked
                            )
                        }
                    }
                )
            }
        }
    }

    private fun onQueryOtherDevicesClicked() {
        lifecycleScope.launch {
            try {
                Log.d(TAG, "Querying Devices")
                val nodes = getCapabilitiesForReachableNodes()
                    .filterValues { MOBILE_CAPABILITY in it || WEAR_CAPABILITY in it }.keys
                Log.d(TAG, "Node - $nodes")
                displayNodes(nodes)
            } catch (cancellationException: CancellationException) {
                throw cancellationException
            } catch (exception: Exception) {
                Log.d(TAG, "Querying nodes failed: $exception")
            }
        }
    }

    private fun onQueryMobileCameraClicked() {
        lifecycleScope.launch {
            try {
                Log.d(TAG, "Querying Camera")
                val nodes = getCapabilitiesForReachableNodes()
                    .filterValues { MOBILE_CAPABILITY in it && CAMERA_CAPABILITY in it }.keys
                Log.d(TAG, "Node - $nodes")
                displayNodes(nodes)
            } catch (cancellationException: CancellationException) {
                Log.d(TAG, "Querying nodes failed: $cancellationException")
                throw cancellationException

            } catch (exception: Exception) {
                Log.d(TAG, "Querying nodes failed: $exception")
            }
        }
    }

    /**
     * Collects the capabilities for all nodes that are reachable using the [CapabilityClient].
     *
     * [CapabilityClient.getAllCapabilities] returns this information as a [Map] from capabilities
     * to nodes, while this function inverts the map so we have a map of [Node]s to capabilities.
     *
     * This form is easier to work with when trying to operate upon all [Node]s.
     */
    private suspend fun getCapabilitiesForReachableNodes(): Map<Node, Set<String>> =
        capabilityClient.getAllCapabilities(CapabilityClient.FILTER_REACHABLE)
            .await()
            // Pair the list of all reachable nodes with their capabilities
            .flatMap { (capability, capabilityInfo) ->
                capabilityInfo.nodes.map { it to capability }
            }
            // Group the pairs by the nodes
            .groupBy(
                keySelector = { it.first },
                valueTransform = { it.second }
            )
            // Transform the capability list for each node into a set
            .mapValues { it.value.toSet() }

    private fun displayNodes(nodes: Set<Node>) {
        val message = if (nodes.isEmpty()) {
            getString(R.string.no_device)
        } else {
            getString(R.string.connected_nodes, nodes.joinToString(", ") { it.displayName })
        }

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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

    companion object {
        private const val TAG = "MainActivityPhone"

        private const val CAMERA_CAPABILITY = "camera"
        private const val WEAR_CAPABILITY = "wear"
        private const val MOBILE_CAPABILITY = "mobile"
    }
}