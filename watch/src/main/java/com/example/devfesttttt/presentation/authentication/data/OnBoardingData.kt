package com.example.devfesttttt.presentation.authentication.data

import com.example.devfesttttt.R

object OnBoardingData {
    fun getItems(): List<OnBoardingItem> {
        return listOf(
            OnBoardingItem(R.drawable.ic_android,
                "Welcome To Devfest"),
            OnBoardingItem(R.drawable.ic_android,
                "We are glad to have you here at the conference"),
            OnBoardingItem(null,
                "Don't forget to check in by tapping the scanner on your watch")
        )
    }

    data class OnBoardingItem(
        val image: Int?,
        val text: String
    )
}