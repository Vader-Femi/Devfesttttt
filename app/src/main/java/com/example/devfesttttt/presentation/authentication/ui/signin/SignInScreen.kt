package com.example.devfesttttt.presentation.authentication.ui.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.material.*
import coil.compose.rememberAsyncImagePainter
import com.example.devfesttttt.R
import com.example.devfesttttt.presentation.authentication.viewmodel.AuthenticationViewModel

@Composable
fun SignInScreen(navController: NavController, viewModel: AuthenticationViewModel) {
    val scrollState = rememberScalingLazyListState()
    val email by remember { mutableStateOf("")}
    val signInText by remember { mutableStateOf("Signing In..")}
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
                Image(
                    painter = rememberAsyncImagePainter(""),
                    contentScale = ContentScale.Crop,
                    contentDescription = "Profile picture",
                    modifier = Modifier
                        .size(74.dp)
                        .clip(CircleShape)
                        .wrapContentSize(align = Alignment.Center),
                )
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

        }
    }

}