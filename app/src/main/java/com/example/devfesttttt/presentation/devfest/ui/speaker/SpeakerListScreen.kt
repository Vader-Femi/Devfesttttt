package com.example.devfesttttt.presentation.devfest.ui.speaker

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.material.*
import coil.compose.rememberAsyncImagePainter
import com.example.devfesttttt.presentation.Screen
import com.example.devfesttttt.presentation.devfest.data.Speaker
import com.example.devfesttttt.presentation.devfest.viewmodel.DevFestViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun SpeakerListScreen(navController: NavController, viewModel: DevFestViewModel) {
    val scrollState = rememberScalingLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val speakers by viewModel.getSpeakers().observeAsState()
    LaunchedEffect(key1 = true) {
        coroutineScope.launch(Dispatchers.IO) {
            viewModel.nukeSpeakerTable()
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
                        text = "Speakers",
                        textAlign = TextAlign.Center
                    )
                }
            }

            speakers?.forEach { speaker ->
                item {
                    Chip(
                        label = {
                            Text(
                                text = speaker.speakerName,
                                maxLines = 1,
                                fontSize = 12.sp,
                                overflow = TextOverflow.Ellipsis,
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        icon = {
                            Image(
                                painter = rememberAsyncImagePainter(speaker.speakerProfilePic),
                                contentScale = ContentScale.Fit,
                                contentDescription = "${speaker.speakerName}'s image",
                                modifier = Modifier
                                    .size(ChipDefaults.LargeIconSize)
                                    .clip(CircleShape)
                                    .wrapContentSize(align = Alignment.Center)
                            )
                        },
                        colors = ChipDefaults.secondaryChipColors(),
                        onClick = {
                            navController.navigate(
                                Screen.SpeakerDetailsScreen.withIdArg(
                                    Pair("speaker_id", speaker.id)
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
    viewModel.insertSpeaker(
        Speaker(
            speakerName = "Moyin Adeniyi",
            speakerProfilePic = "https://pbs.twimg.com/profile_images/1631984645959983104/aYhJX0-0_400x400.jpg",
            speakerAbout = "Senior Android Engineer\nGoogle Developer Expert for Android\n8x Marathoner \uD83C\uDFC3\uD83C\uDFFE\u200D♀️\nSprichst du Deutsch?",
            speakerFacebook = "https://www.facebook.com/",
            speakerTwitter = "https://twitter.com/moyheen",
            speakerLinkedin = "https://www.linkedin.com/in/moyinoluwa"
        )
    )

    viewModel.insertSpeaker(
        Speaker(
            speakerName = "Hannah Olukoye",
            speakerProfilePic = "https://pbs.twimg.com/profile_images/1609576552617938945/rR_--ww0_400x400.jpg",
            speakerAbout = "@GoogleDevExpert for Android\nEngineering Manager at taxfix_de\nAndroid Engineer at Andela",
            speakerFacebook = "https://www.facebook.com/",
            speakerTwitter = "https://twitter.com/hannah_omu",
            speakerLinkedin = "https://ke.linkedin.com/in/hannaholukoye"
        )
    )

    viewModel.insertSpeaker(
        Speaker(
            speakerName = "Tunji Dahunsi",
            speakerProfilePic = "https://pbs.twimg.com/profile_images/1504815529848152074/iA9Q_QME_400x400.jpg",
            speakerAbout = "Dev rel bloke at Google working on app architecture",
            speakerFacebook = "https://www.facebook.com/",
            speakerTwitter = "https://twitter.com/Tunji_D",
            speakerLinkedin = "https://www.linkedin.com/in/tunji-dahunsi"
        )
    )

    viewModel.insertSpeaker(
        Speaker(
            speakerName = "Chisom Nwokwu",
            speakerProfilePic = "https://pbs.twimg.com/profile_images/1632459475176095746/AyCCEvbH_400x400.jpg",
            speakerAbout = "Software Engineer at Microsoft\nNewest Author\nYouTuber\nEx at BankOfAmerica",
            speakerFacebook = "https://www.facebook.com/",
            speakerTwitter = "https://twitter.com/tech_queen",
            speakerLinkedin = "https://ng.linkedin.com/in/chisom-c-nwokwu-4b5787186"
        )
    )

}