package com.example.devfesttttt.presentation.devfest.ui.speaker

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.material.*
import coil.compose.rememberAsyncImagePainter
import com.example.devfesttttt.R
import com.example.devfesttttt.presentation.devfest.viewmodel.DevFestViewModel
import com.google.accompanist.pager.ExperimentalPagerApi


@ExperimentalPagerApi
@Composable
fun SpeakerDetailsScreen(
    navController: NavController,
    viewModel: DevFestViewModel,
    speaker_id: Int,
) {
    val scrollState = rememberScalingLazyListState()
    val speaker by viewModel.getSpeakerById(speaker_id).observeAsState()
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
                            .padding(bottom = 8.dp),
                        text = "${speaker?.speakerName}",
                        textAlign = TextAlign.Center
                    )
                }
            }

            item {
                Image(
                    painter = rememberAsyncImagePainter(speaker?.speakerProfilePic),
                    contentScale = ContentScale.Crop,
                    contentDescription = "${speaker?.speakerName}'s image",
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
                    text = "${speaker?.speakerAbout}",
                    fontSize = 12.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
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

                    Image(
                        painterResource(id = R.drawable.ic_facebook),
                        contentScale = ContentScale.Fit,
                        contentDescription = "${speaker?.speakerName}'s Facebook Profile",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .padding(2.dp)
                            .clickable {
                                context.launchWebsite(speaker?.speakerFacebook)
                            }
                            .wrapContentSize(align = Alignment.Center)
                    )

                    Image(
                        painterResource(id = R.drawable.ic_twitter),
                        contentScale = ContentScale.Fit,
                        contentDescription = "${speaker?.speakerName}'s Twitter Profile",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .padding(2.dp)
                            .clickable {
                                context.launchWebsite(speaker?.speakerTwitter)
                            }
                            .wrapContentSize(align = Alignment.Center)
                    )

                    Image(
                        painterResource(id = R.drawable.ic_linkedin),
                        contentScale = ContentScale.Fit,
                        contentDescription = "${speaker?.speakerName}'s Linkedin Profile",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .padding(2.dp)
                            .clickable {
                                context.launchWebsite(speaker?.speakerLinkedin)
                            }
                            .wrapContentSize(align = Alignment.Center)
                    )

                }
            }
        }

    }

}

fun Context.launchWebsite(link: String?) {
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
}