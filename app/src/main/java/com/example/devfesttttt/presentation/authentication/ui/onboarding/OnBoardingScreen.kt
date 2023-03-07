package com.example.devfesttttt.presentation.authentication.ui.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.material.*
import com.example.devfesttttt.presentation.Screen
import com.example.devfesttttt.presentation.authentication.data.OnBoardingData
import com.example.devfesttttt.presentation.authentication.viewmodel.AuthenticationViewModel
import com.google.accompanist.pager.*

@ExperimentalPagerApi
@Composable
fun OnBoardingScreen(navController: NavController, viewModel: AuthenticationViewModel) {
    val scrollState = rememberScalingLazyListState()
    val pagerState = rememberPageState()
    ScalingLazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize(),
        state = scrollState
    ) {
        item {
            OnBoardingViewPager(
                navController,
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
                          navController.navigate(Screen.SignInConfirmationScreen.route)
                    },
                    modifier = Modifier.
                    size(ButtonDefaults.ExtraSmallButtonSize)
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

