package com.example.devfesttttt.presentation.authentication.ui.signin

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
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
import com.example.devfesttttt.presentation.authentication.viewmodel.AuthenticationViewModel
import com.example.devfesttttt.presentation.devfest.ui.DevFestActivity
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import kotlin.reflect.KSuspendFunction1

@Composable
fun SignInScreen(
    navController: NavController,
    viewModel: AuthenticationViewModel,
    profileImage: Bitmap?,
    profileEmail: String?,
    profileName: String?,
    onSendPairingStatus: KSuspendFunction1<Boolean, Unit>
) {
    val scrollState = rememberScalingLazyListState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var proceedButtonPosition by remember { mutableStateOf(0F) }
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
                            .padding(bottom = 0.dp)
                            .scrollAway(scrollState),
                        text = "",
                        textAlign = TextAlign.Center
                    )
                }
            }

            item {
                if (profileImage == null) {
                    Image(
                        painter = painterResource(id = R.drawable.photo_placeholder),
                        contentScale = ContentScale.Crop,
                        contentDescription = "Profile picture",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .wrapContentSize(align = Alignment.Center),
                    )
                } else {
                    Image(
                        bitmap = profileImage.asImageBitmap(),
                        contentScale = ContentScale.Crop,
                        contentDescription = "Profile picture",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .wrapContentSize(align = Alignment.Center)
                    )
                }

            }

            if (profileImage != null &&
                profileEmail != null &&
                profileName != null
            ) {

                item {
                    LaunchedEffect(key1 = true){
                        Toast.makeText(context, "Pairing Complete\nClick Proceed to Continue", Toast.LENGTH_SHORT).show()
                        scrollState.scrollToItem(proceedButtonPosition.roundToInt())
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }

                item {
                    Text(
                        text = "Welcome, $profileName",
                        fontSize = 12.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }

                item {
                    Text(
                        text = profileEmail,
                        fontSize = 12.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(4.dp))
                }

                item {
                    AnimatedVisibility(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp, 4.dp, 6.dp, 4.dp)
                            .onGloballyPositioned { coordinates ->
                                proceedButtonPosition = coordinates.positionInParent().y
                            },
                        visible = true
                    ) {
                        Button(
                            colors = ButtonDefaults.secondaryButtonColors(),
                            onClick = {
                                profileImage.let {
                                    viewModel.profileImage(it)
                                }
                                profileEmail.let {
                                    viewModel.userEmail(it)
                                }
                                profileName.let {
                                    viewModel.userName(it)
                                }
                                coroutineScope.launch {
                                    viewModel.userSignedIn(true)
                                    onSendPairingStatus(true)
                                }
                                context.goToDevFestActivity()
                            },
                            modifier = Modifier
                                .size(ButtonDefaults.ExtraSmallButtonSize)
                                .padding(horizontal = 12.dp)
                        ) {
                            Text(text = "Proceed")
                        }
                    }
                }
            } else {
                item {
                    Text(
                        text = "Sign In on your phone\nThen click the \"Send to Wear\" button",
                        fontSize = 12.sp,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }

        }
    }

}

private fun Context.goToDevFestActivity() {
    Intent(this, DevFestActivity::class.java).also { intent ->
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}