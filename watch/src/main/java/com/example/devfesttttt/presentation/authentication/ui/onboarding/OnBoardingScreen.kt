package com.example.devfesttttt.presentation.authentication.ui.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.foundation.*
import androidx.wear.compose.material.*
import com.example.devfesttttt.presentation.Screen
import com.example.devfesttttt.presentation.authentication.data.OnBoardingData
import com.example.devfesttttt.presentation.authentication.viewmodel.AuthenticationViewModel
import com.google.accompanist.pager.*

@ExperimentalPagerApi
@Composable
fun OnBoardingScreen(navController: NavController, viewModel: AuthenticationViewModel) {
    val pagerState = rememberPageState()
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {

        OnBoardingViewPager(
            navController,
            item = OnBoardingData.getItems(),
            pagerState = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        )
        HorizontalPagerIndicator(
            modifier = Modifier
                .padding(bottom = 7.dp)
                .align(Alignment.BottomCenter),
            pagerState = pagerState,
            inactiveColor = Color.Gray,
            activeColor = Color.White,
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun OnBoardingViewPager(
    navController: NavController,
    item: List<OnBoardingData.OnBoardingItem>,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
) {

    HorizontalPager(
        state = pagerState,
        count = item.count()
    ) { page ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
        ) {
            if (item[page].image != null) {
//                Icon(
//                    modifier = Modifier,
//                    painter = painterResource(id = item[page].image!!),
//                    contentDescription = "Devfest Logo"
//                )
                Image(
                    painter = painterResource(id = item[page].image!!),
                    contentScale = ContentScale.Crop,
                    contentDescription = "Devfest Logo",
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                )
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                text = item[page].text,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 14.sp
                )
            )
            AnimatedVisibility(
                modifier = Modifier.fillMaxWidth(0.8f),
                visible = pagerState.currentPage == 2
            ) {
                Button(
                    colors = ButtonDefaults.secondaryButtonColors(),
                    onClick = {
                        navController.navigate(Screen.SignInConfirmationScreen.route)
                    },
                    modifier = Modifier
                        .size(ButtonDefaults.ExtraSmallButtonSize)
                        .padding(horizontal = 12.dp)
                ) {
                    Text(text = "Let's go")
                }

            }

        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun rememberPageState(
    @androidx.annotation.IntRange(from = 0) initialPage: Int = 0,
): PagerState = rememberSaveable(saver = PagerState.Saver) {
    PagerState(
        currentPage = initialPage,
    )
}

