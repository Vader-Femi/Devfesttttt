@file:OptIn(ExperimentalPagerApi::class)

package com.example.devfesttttt.presentation.devfest.ui.profile

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.material.*
import com.example.devfesttttt.R
import com.example.devfesttttt.presentation.authentication.ui.AuthenticationActivity
import com.example.devfesttttt.presentation.devfest.viewmodel.DevFestViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.launch


@ExperimentalPagerApi
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: DevFestViewModel,
) {
    val scrollState = rememberScalingLazyListState()
    var userName by remember { mutableStateOf("") }
    var userEmail by remember { mutableStateOf("") }
    var userProfile by remember { mutableStateOf<Bitmap?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        coroutineScope.launch {
            userName = viewModel.userName()
            userEmail = viewModel.userEmail()
            userProfile = viewModel.profileImage()
        }
    }
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
                ListHeader {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        text = "Profile",
                        textAlign = TextAlign.Center
                    )
                }
            }

            item {
                if (userProfile == null) {
                    Image(
                        painter = painterResource(id = R.drawable.photo_placeholder),
                        contentScale = ContentScale.Crop,
                        contentDescription = "Profile picture",
                        modifier = Modifier
                            .size(74.dp)
                            .clip(CircleShape)
                            .wrapContentSize(align = Alignment.Center),
                    )
                } else {
                    Image(
                        bitmap = userProfile!!.asImageBitmap(),
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

            if (userEmail != "" && userName != "") {

                item {
                    Text(
                        text = "Welcome, $userName",
                        fontSize = 12.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
                item {
                    Text(
                        text = userEmail,
                        fontSize = 12.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
                item {
                    AnimatedVisibility(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp, 4.dp, 6.dp, 4.dp),
                        visible = true
                    ) {
                        Button(
                            colors = ButtonDefaults.secondaryButtonColors(),
                            onClick = {
                                coroutineScope.launch {
                                    viewModel.userSignedIn(false)
                                }
                                context.goToAuthenticationActivity()
                            },
                            modifier = Modifier
                                .size(ButtonDefaults.ExtraSmallButtonSize)
                                .padding(horizontal = 12.dp)
                        ) {
                            Text(text = "Sign Out")
                        }
                    }
                }
            } else {
                item {
                    Text(
                        text = "Not Signed In\nSign in with your phone to view your profile",
                        fontSize = 12.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }

                item {
                    AnimatedVisibility(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp, 4.dp, 6.dp, 4.dp),
                        visible = true
                    ) {
                        Button(
                            colors = ButtonDefaults.secondaryButtonColors(),
                            onClick = {
                                context.goToAuthenticationActivity()
                            },
                            modifier = Modifier
                                .size(ButtonDefaults.ExtraSmallButtonSize)
                                .padding(horizontal = 12.dp)
                        ) {
                            Text(text = "Go to Sign In")
                        }
                    }
                }
            }
        }

    }

}

private fun Context.goToAuthenticationActivity() {
    Intent(this, AuthenticationActivity::class.java).also {
        it.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}