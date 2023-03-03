package com.example.devfesttttt.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.material.*
import com.google.accompanist.pager.*

@ExperimentalPagerApi
@Composable
fun OnBoardingScreen(navController: NavController) {
    val scrollState = rememberScalingLazyListState()
    val coroutineScope = rememberScalingLazyListState()
    val pagerState = rememberPageState()
    ScalingLazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize(),
        state = scrollState
    ) {
        item {
            OnBoardingViewPager(
                item = OnBoardingData.getItems(),
                pagerState = pagerState,
                modifier = Modifier.fillMaxSize()
            )
        }
//        HorizontalPagerIndicator(
//            modifier = Modifier
//                .padding(0.dp, 20.dp, 0.dp, 20.dp)
//                .fillMaxWidth(),
//            pagerState = pagerState,
//            indicatorHeight = 8.dp,
//            indicatorWidth = 12.dp,
//            inactiveColor = MaterialTheme.colors.primaryVariant,
//            activeColor = MaterialTheme.colors.primary,
//            spacing = 12.dp
//        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun OnBoardingViewPager(
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
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = modifier
        ) {
            if (item[page].image != null) {
                Icon(
                    modifier = Modifier,
                    painter = painterResource(id = item[page].image!!),
                    contentDescription = "Devfest Logo"
                )
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                text = item[page].text,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 16.sp
                )
            )
            AnimatedVisibility(
                modifier = Modifier.fillMaxWidth(0.8f),
                visible = pagerState.currentPage == 2
            ) {
                Button(
                    colors = ButtonDefaults.secondaryButtonColors(),
                    onClick = {
//                            navController.navigate(Screen.LogInScreen.route)
                    },
                ) {
                    Text(text = "Let's gooooo")
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