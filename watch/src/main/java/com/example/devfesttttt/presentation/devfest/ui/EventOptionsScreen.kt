package com.example.devfesttttt.presentation.devfest.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.material.*
import com.example.devfesttttt.R
import com.example.devfesttttt.presentation.Screen

@Composable
fun EventOptionsScreen(navController: NavController) {
    val scrollState = rememberScalingLazyListState()
    val iconModifier = Modifier
        .size(24.dp)
        .wrapContentSize(align = Alignment.Center)

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
                        text = "DevFest 2023",
                        textAlign = TextAlign.Center
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {

                        Button(
                            modifier = Modifier.size(ButtonDefaults.DefaultButtonSize),
                            onClick = { navController.navigate(Screen.AgendaListScreen.route) }) {
                            Icon(
                                painterResource(id = R.drawable.ic_agenda),
                                contentDescription = "Agenda Icon",
                                modifier = iconModifier
                            )
                        }
                        Text(
                            text = "Agenda",
                            fontSize = 12.sp,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            textAlign = TextAlign.Center
                        )
                    }

                    Column {
                        Button(
                            modifier = Modifier.size(ButtonDefaults.DefaultButtonSize),
                            onClick = { navController.navigate(Screen.SessionListScreen.route) }) {
                            Icon(
                                painterResource(id = R.drawable.ic_session),
                                contentDescription = "Session Icon",
                                modifier = iconModifier
                            )
                        }
                        Text(
                            text = "Session",
                            fontSize = 12.sp,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            textAlign = TextAlign.Center
                        )
                    }

                }
            }

            item {
                Spacer(modifier = Modifier.height(4.dp))
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Button(
                            modifier = Modifier.size(ButtonDefaults.DefaultButtonSize),
                            onClick = { navController.navigate(Screen.SpeakerListScreen.route) }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_speaker),
                                contentDescription = "Speaker Icon",
                                modifier = iconModifier
                            )
                        }
                        Text(
                            text = "Speaker",
                            fontSize = 12.sp,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            textAlign = TextAlign.Center
                        )
                    }

                    Column{
                        Button(
                            modifier = Modifier.size(ButtonDefaults.DefaultButtonSize),
                            onClick = { navController.navigate(Screen.ProfileScreen.route) }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_profile),
                                contentDescription = "Profile Icon",
                                modifier = iconModifier
                            )
                        }
                        Text(
                            text = "Profile",
                            fontSize = 12.sp,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

        }
    }
}