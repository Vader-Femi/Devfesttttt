package com.example.devfesttttt.presentation.devfest.ui.agenda

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.material.*
import com.example.devfesttttt.R
import com.example.devfesttttt.presentation.RoomConverters
import com.example.devfesttttt.presentation.devfest.viewmodel.DevFestViewModel
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalPagerApi
@Composable
fun AgendaDetailsScreen(navController: NavController, viewModel: DevFestViewModel, agenda_id: Int) {
    val scrollState = rememberScalingLazyListState()
    val agenda by viewModel.getAgendaById(agenda_id).observeAsState()
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
                        text = "${agenda?.title}",
                        textAlign = TextAlign.Center
                    )
                }
            }

            item {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_speaker),
                        contentDescription = "Speaker Icon",
                        modifier = Modifier
                            .size(ChipDefaults.IconSize)
                            .wrapContentSize(align = Alignment.CenterStart),
                    )
                    Text(
                        text = "${agenda?.speaker}",
                        maxLines = 2,
                        fontSize = 12.sp,
                        modifier = Modifier.align(Alignment.CenterVertically)
                            .padding(4.dp),
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }

            item {
                Divider(color = Color.White, thickness = 1.dp)
            }

            item {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_time),
                        contentDescription = "Time Icon",
                        modifier = Modifier
                            .size(ChipDefaults.IconSize)
                            .wrapContentSize(align = Alignment.CenterStart),
                    )
                    Text(
                        text = "${
                            agenda?.startDateTime?.let {
                                RoomConverters.dateToString(it).substring(11)
                                    .replace(" ", "")
                            }
                        } - " +
                                agenda?.endDateTime?.let {
                                    RoomConverters.dateToString(it).substring(11)
                                        .replace(" ", "")
                                },
                        maxLines = 2,
                        fontSize = 12.sp,
                        modifier = Modifier.align(Alignment.CenterVertically)
                            .padding(4.dp),
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }

            item {
                Divider(color = Color.White, thickness = 1.dp)
            }

            item {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_location),
                        contentDescription = "Location Icon",
                        modifier = Modifier
                            .size(ChipDefaults.IconSize)
                            .wrapContentSize(align = Alignment.CenterStart),
                    )
                    Text(
                        text = "${agenda?.venue}",
                        maxLines = 2,
                        fontSize = 12.sp,
                        modifier = Modifier.align(Alignment.CenterVertically)
                            .padding(4.dp),
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }

        }
    }
}