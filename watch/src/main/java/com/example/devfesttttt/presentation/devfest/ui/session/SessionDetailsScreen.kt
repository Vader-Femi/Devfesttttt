package com.example.devfesttttt.presentation.devfest.ui.session

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.material.*
import com.example.devfesttttt.presentation.devfest.viewmodel.DevFestViewModel
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalPagerApi
@Composable
fun SessionDetailsScreen(
    navController: NavController,
    viewModel: DevFestViewModel,
    session_id: Int,
) {
    val scrollState = rememberScalingLazyListState()
    val session by viewModel.getSessionById(session_id).observeAsState()
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
                        text = "${session?.sessionName}",
                        textAlign = TextAlign.Center
                    )
                }
            }

            session?.sessionTalks?.forEach { sessionTalk ->
                item {
                    Chip(
                        label = {
                            Text(
                                text = "${sessionTalk.startDateTime.replace(" ", "")} -" +
                                        " ${sessionTalk.endDateTime.replace(" ", "")}",
                                maxLines = 1,
                                fontSize = 12.sp,
                                overflow = TextOverflow.Ellipsis,
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        secondaryLabel = {
                            Text(
                                text = sessionTalk.title,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        icon = {
                            session?.sessionIcon?.let{
                                Icon(
                                    painter = painterResource(id = it),
                                    contentDescription = "${sessionTalk.title}'s Icon",
                                    modifier = Modifier
                                        .size(ChipDefaults.IconSize)
                                        .wrapContentSize(align = Alignment.Center),
                                )
                            }


                        },
                        colors = ChipDefaults.secondaryChipColors(),
                        onClick = {},
                    )
                }
            }
        }

    }

}