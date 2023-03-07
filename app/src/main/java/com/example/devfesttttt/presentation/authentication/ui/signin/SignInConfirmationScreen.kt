package com.example.devfesttttt.presentation.authentication.ui.signin

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.material.*
import com.example.devfesttttt.R
import com.example.devfesttttt.presentation.Screen
import com.example.devfesttttt.presentation.authentication.viewmodel.AuthenticationViewModel
import com.example.devfesttttt.presentation.devfest.ui.DevFestActivity

@Composable
fun SignInConfirmationScreen(navController: NavController, viewModel: AuthenticationViewModel) {
    val scrollState = rememberScalingLazyListState()
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
                            .padding(bottom = 8.dp)
                            .scrollAway(scrollState),
                        text = "Sign In",
                        textAlign = TextAlign.Center
                    )
                }
            }

            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    fontSize = 12.sp,
                    text = "See conference agenda, session, and speakers",
                    textAlign = TextAlign.Center
                )
            }

            item {
                Chip(
                    label = {
                        Text(
                            text = "Sign in using companion app",
                            maxLines = 2,
                            fontSize = 12.sp,
                            overflow = TextOverflow.Ellipsis,
                        )
                    },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_phone_link),
                            contentDescription = "Phone Link Icon",
                            modifier = Modifier
                                .size(ChipDefaults.LargeIconSize)
                                .wrapContentSize(align = Alignment.Center)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ChipDefaults.secondaryChipColors(),
                    onClick = { navController.navigate(Screen.SignInScreen.route) },
                )
            }

            item {
                Spacer(modifier = Modifier.height(2.dp))
            }


            item {
                Chip(
                    label = {
                        Text(
                            text = "Continue without signing in",
                            maxLines = 2,
                            fontSize = 12.sp,
                            overflow = TextOverflow.Ellipsis,
                        )
                    },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_cancel),
                            contentDescription = "Continue without signing in Icon",
                            modifier = Modifier
                                .size(ChipDefaults.LargeIconSize)
                                .wrapContentSize(align = Alignment.Center)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ChipDefaults.secondaryChipColors(),
                    onClick = { context.goToDevFestActivity() },
                )
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