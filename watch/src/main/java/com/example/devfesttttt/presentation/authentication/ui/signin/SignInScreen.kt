package com.example.devfesttttt.presentation.authentication.ui.signin

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.material.*
import com.example.devfesttttt.R
import com.example.devfesttttt.presentation.Screen
import com.example.devfesttttt.presentation.authentication.viewmodel.AuthenticationViewModel
import com.example.devfesttttt.presentation.devfest.ui.DevFestActivity

@Composable
fun SignInScreen(
    navController: NavController,
    viewModel: AuthenticationViewModel,
    image: Bitmap?,
    email: String?,
) {
    val scrollState = rememberScalingLazyListState()
    val signInText by remember { mutableStateOf("Signing In..") }
    val context = LocalContext.current
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
                            .size(90.dp)
                            .clip(CircleShape)
                            .wrapContentSize(align = Alignment.Center),
                    )
                }

            }

            item {
                Spacer(modifier = Modifier.height(4.dp))
            }

            if (email != null) {
                item {
                    Text(
                        text = "Welcome, i'll insert_name_here",
                        fontSize = 12.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(0.dp))
                }

                item {
                    Text(
                        text = email,
                        fontSize = 12.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }

            if (email != null) {

                item {
                    Spacer(modifier = Modifier.height(4.dp))
                }

                item {
                    Button(
                        colors = ButtonDefaults.secondaryButtonColors(),
                        onClick = {
                            context.goToDevFestActivity()
                        },
                        modifier = Modifier
                            .padding(horizontal = 4.dp, vertical = 8.dp)
                            .fillMaxWidth()
                    ) {
                        Text(text = "Proceed")
                    }
                }
            } else {
                item {
                    Text(
                        text = signInText,
                        fontSize = 12.sp,
                        modifier = Modifier.fillMaxWidth(),
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