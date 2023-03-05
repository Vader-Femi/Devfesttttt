package com.example.devfesttttt.presentation.devfest.ui

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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.wear.compose.material.*
import com.example.devfesttttt.R.drawable
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
            viewModel.deleteAgenda(
                Agenda(
                    startDateTime = LocalDateTime.now(),
                    endDateTime = LocalDateTime.now().plusHours(1),
                    venue = "Hall 1",
                    title = "Wear OS"
                )
            )

            viewModel.deleteAgenda(
                Agenda(
                    startDateTime = LocalDateTime.now().plusHours(1),
                    endDateTime = LocalDateTime.now().plusHours(2),
                    venue = "Hall 2",
                    title = "Wear OS 2"
                )
            )
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
                    Chip(
                        label = {
                            if (eventIsHappeningNow(event.startDateTime, event.endDateTime))
                                Text(text = "Happening Now")
                            else Text(
                                text = "${event.startDateTime.hour}:${event.startDateTime.minute} " +
                                        "- ${event.endDateTime.hour}:${event.endDateTime.minute}"
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        secondaryLabel = { Text(text = event.title) },
                        icon = {
                            Icon(
                                painter = painterResource(id = drawable.ic_android),
                                contentDescription = "${event.title}'s Icon",
                                modifier = Modifier
                                    .size(ChipDefaults.LargeIconSize)
                                    .wrapContentSize(align = Alignment.Center)
                            )
                        },
                        colors = ChipDefaults.secondaryChipColors(),
//                        titleColor =
//                        if (eventIsHappeningNow(event.startDateTime, event.endDateTime))
//                            MaterialTheme.colors.primary else MaterialTheme.colors.onSurface,
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

@Composable
fun eventIsHappeningNow(startDateTime: LocalDateTime, endDateTime: LocalDateTime): Boolean {
    return startDateTime.isBefore(LocalDateTime.now()) &&
            endDateTime.isAfter(LocalDateTime.now())
}