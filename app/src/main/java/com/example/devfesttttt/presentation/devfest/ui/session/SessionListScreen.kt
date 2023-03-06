package com.example.devfesttttt.presentation.devfest.ui.session

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.material.*
import com.example.devfesttttt.R
import com.example.devfesttttt.R.drawable
import com.example.devfesttttt.presentation.RoomConverters
import com.example.devfesttttt.presentation.Screen
import com.example.devfesttttt.presentation.devfest.data.Session
import com.example.devfesttttt.presentation.devfest.data.SessionTalk
import com.example.devfesttttt.presentation.devfest.viewmodel.DevFestViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

private val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH)

@ExperimentalPagerApi
@Composable
fun SessionListScreen(navController: NavController, viewModel: DevFestViewModel) {
    val scrollState = rememberScalingLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val session by viewModel.getSession().observeAsState()
    LaunchedEffect(key1 = true) {
        coroutineScope.launch(Dispatchers.IO) {
            viewModel.nukeSessionTable()
            insertStuffInDB(viewModel)
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
                        text = "Session",
                        textAlign = TextAlign.Center
                    )
                }
            }

            session?.forEach { event ->
                item {
                    Chip(
                        label = {
                            Text(
                                text = event.sessionName,
                                maxLines = 1,
                                fontSize = 12.sp,
                                overflow = TextOverflow.Ellipsis,
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        icon = {
                            Icon(
                                painter = painterResource(id = event.sessionIcon),
                                contentDescription = "${event.sessionName}'s Icon",
                                modifier = Modifier
                                    .size(ChipDefaults.IconSize)
                                    .wrapContentSize(align = Alignment.Center),
                            )
                        },
                        colors = ChipDefaults.secondaryChipColors(),
                        onClick = {
                            navController.navigate(
                                Screen.SessionDetailsScreen.withIdArg(
                                    Pair("session_id", event.id)
                                )
                            )
                        },
                    )
                }
            }

        }
    }
}

fun insertStuffInDB(viewModel: DevFestViewModel) {
    viewModel.insertSession(
        Session(
            sessionName = "Android",
            sessionIcon = R.drawable.ic_android,
            sessionTalks = listOf(
                SessionTalk(
                    startDateTime =
                    LocalDateTime.now().minusHours(2).format(timeFormatter),
                    endDateTime =
                    LocalDateTime.now().minusHours(1).format(timeFormatter),
                    title = "Developing For Wear OS"
                ),
                SessionTalk(
                    startDateTime =
                    LocalDateTime.now().minusHours(1).format(timeFormatter),
                    endDateTime =
                    LocalDateTime.now().plusHours(2).format(timeFormatter),
                    title = "Intro To Compose"
                ),
                SessionTalk(
                    startDateTime =
                    LocalDateTime.now().plusHours(2).format(timeFormatter),
                    endDateTime =
                    LocalDateTime.now().plusHours(3).format(timeFormatter),
                    title = "You are not ready for KMM"
                )
            )
        )
    )

    viewModel.insertSession(
        Session(
            sessionName = "Cloud",
            sessionIcon = R.drawable.ic_cloud,
            sessionTalks = listOf(
                SessionTalk(
                    startDateTime =
                    LocalDateTime.now().minusHours(2).format(timeFormatter),
                    endDateTime =
                    LocalDateTime.now().minusHours(1).format(timeFormatter),
                    title = "Data Structures and Algorithm"
                )
            )
        )
    )

    viewModel.insertSession(
        Session(
            sessionName = "Design",
            sessionIcon = R.drawable.ic_design,
            sessionTalks = listOf(
                SessionTalk(
                    startDateTime =
                    LocalDateTime.now().minusHours(2).format(timeFormatter),
                    endDateTime =
                    LocalDateTime.now().minusHours(1).format(timeFormatter),
                    title = "UI/UI and all that"
                )
            )
        )
    )

    viewModel.insertSession(
        Session(
            sessionName = "DevOps",
            sessionIcon = R.drawable.ic_dev_ops,
            sessionTalks = listOf(
                SessionTalk(
                    startDateTime =
                    LocalDateTime.now().minusHours(2).format(timeFormatter),
                    endDateTime =
                    LocalDateTime.now().minusHours(1).format(timeFormatter),
                    title = "DevOps Stuff"
                )
            )
        )
    )

}