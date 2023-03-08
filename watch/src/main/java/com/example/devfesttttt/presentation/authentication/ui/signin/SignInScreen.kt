package com.example.devfesttttt.presentation.authentication.ui.signin

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.material.*
import coil.compose.rememberAsyncImagePainter
import com.example.devfesttttt.R
import com.example.devfesttttt.presentation.authentication.Event
import com.example.devfesttttt.presentation.authentication.viewmodel.AuthenticationViewModel
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.wearable.Asset
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.Wearable
import java.io.InputStream

@Composable
fun SignInScreen(
    navController: NavController,
    viewModel: AuthenticationViewModel,
    events: List<Event>,
    image: Bitmap?,
    onQueryOtherDevicesClicked: () -> Unit,
    onQueryMobileCameraClicked: () -> Unit,
) {
    val scrollState = rememberScalingLazyListState()
    val email by remember { mutableStateOf("") }
    val signInText by remember { mutableStateOf("Signing In..") }
    Scaffold(
        timeText = {
            TimeText(modifier = Modifier.scrollAway(scrollState))
        },
        vignette = { Vignette(vignettePosition = VignettePosition.TopAndBottom) },
        positionIndicator = { PositionIndicator(scalingLazyListState = scrollState) }
    ) {
        ScalingLazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = scrollState
        ) {

            item {
                if (image == null) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_app_logo),
                        contentScale = ContentScale.Crop,
                        contentDescription = "Profile picture",
                        modifier = Modifier
                            .size(74.dp)
                            .clip(CircleShape)
                            .wrapContentSize(align = Alignment.Center),
                    )
                } else {
                    Image(
                        image.asImageBitmap(),
                        contentScale = ContentScale.Crop,
                        contentDescription = "Profile picture",
                        modifier = Modifier
                            .size(74.dp)
                            .clip(CircleShape)
                            .wrapContentSize(align = Alignment.Center),
                    )
                }

            }

            item {
                Spacer(modifier = Modifier.height(4.dp))
            }

            item {
                Text(
                    text = signInText,
                    fontSize = 12.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            item {
                Spacer(modifier = Modifier.height(4.dp))
            }

            item {
                Text(
                    text = email,
                    fontSize = 12.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            item {
                Button(
                    onClick = onQueryOtherDevicesClicked,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(id = R.string.query_other_devices),
                        modifier = Modifier.padding(4.dp))
                }
            }

            item {
                Button(
                    onClick = onQueryMobileCameraClicked,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(id = R.string.query_mobile_camera),
                        modifier = Modifier.padding(4.dp))
                }
            }

            if (events.isEmpty()) {
                item {
                    Text(
                        stringResource(id = R.string.waiting),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                items(events) { event ->
                    Card(
                        onClick = {},
                        enabled = false
                    ) {
                        Column {
                            Text(
                                stringResource(id = event.title),
                                style = MaterialTheme.typography.title3
                            )
                            Text(
                                event.text,
                                style = MaterialTheme.typography.body2
                            )
                        }
                    }
                }
            }

        }
    }

}