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
                "Listen to Moyin and develop for wear OS")
        )
    }

    data class OnBoardingItem(
        val image: Int?,
        val text: String
    )
}