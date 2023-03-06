package com.example.devfesttttt.presentation.devfest.ui.agenda

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
import com.example.devfesttttt.R.drawable
import com.example.devfesttttt.presentation.RoomConverters
import com.example.devfesttttt.presentation.Screen
import com.example.devfesttttt.presentation.devfest.data.Agenda
import com.example.devfesttttt.presentation.devfest.viewmodel.DevFestViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@ExperimentalPagerApi
@Composable
fun AgendaListScreen(navController: NavController, viewModel: DevFestViewModel) {
    val scrollState = rememberScalingLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val agenda by viewModel.getAgenda().observeAsState()
    LaunchedEffect(key1 = true) {
        coroutineScope.launch(Dispatchers.IO) {
            viewModel.nukeAgendaTable()
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
                        text = "Agenda",
                        textAlign = TextAlign.Center
                    )
                }
            }

            agenda?.forEach { event ->
                item {
                    val eventIsHappeningNow = event.startDateTime.isBefore(LocalDateTime.now()) &&
                            event.endDateTime.isAfter(LocalDateTime.now())
                    Chip(
                        label = {
                            if (eventIsHappeningNow)
                                Text(
                                    text = "Happening Now",
                                    maxLines = 1,
                                    fontSize = 12.sp,
                                    overflow = TextOverflow.Ellipsis,
                                    color = MaterialTheme.colors.primary
                                )
                            else Text(
                                text = "${
                                    RoomConverters.dateToString(event.startDateTime).substring(11)
                                        .replace(" ", "")
                                } - " +
                                        RoomConverters.dateToString(event.endDateTime).substring(11)
                                            .replace(" ", ""),
                                maxLines = 1,
                                fontSize = 12.sp,
                                overflow = TextOverflow.Ellipsis,
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        secondaryLabel = {
                            Text(
                                text = event.title,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        icon = {
                            if (eventIsHappeningNow) {
                                Icon(
                                    painter = painterResource(id = drawable.ic_android),
                                    contentDescription = "${event.title}'s Icon",
                                    modifier = Modifier
                                        .size(ChipDefaults.IconSize)
                                        .wrapContentSize(align = Alignment.Center),
                                    tint = MaterialTheme.colors.primary
                                )
                            } else {
                                Icon(
                                    painter = painterResource(id = drawable.ic_android),
                                    contentDescription = "${event.title}'s Icon",
                                    modifier = Modifier
                                        .size(ChipDefaults.IconSize)
                                        .wrapContentSize(align = Alignment.Center),
                                )
                            }

                        },
                        colors = ChipDefaults.secondaryChipColors(),
                        onClick = {
                            navController.navigate(
                                Screen.AgendaDetailsScreen.withIdArg(
                                    Pair("agenda_id", event.id)
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
    viewModel.insertAgenda(
        Agenda(
            startDateTime = LocalDateTime.now().minusHours(2),
            endDateTime = LocalDateTime.now().minusHours(1),
            venue = "Hall 1",
            speaker = "Hannah Olukoye",
            title = "Jetpack Compose"
        )
    )

    viewModel.insertAgenda(
        Agenda(
            startDateTime = LocalDateTime.now().minusHours(1),
            endDateTime = LocalDateTime.now().plusHours(2),
            venue = "Hall 2",
            speaker = "Moyin Adeyemi",
            title = "Developing For Wear OS"
        )
    )

    viewModel.insertAgenda(
        Agenda(
            startDateTime = LocalDateTime.now().plusHours(2),
            endDateTime = LocalDateTime.now().plusHours(3),
            venue = "Hall 3",
            speaker = "Tunji Dahunsi",
            title = "KMM"
        )
    )

    viewModel.insertAgenda(
        Agenda(
            startDateTime = LocalDateTime.now().plusHours(3),
            endDateTime = LocalDateTime.now().plusHours(4),
            venue = "Hall 4",
            speaker = "Chisom Nwokwu",
            title = "Android"
        )
    )
}