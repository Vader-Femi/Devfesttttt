package com.example.devfesttttt.presentation.authentication.data

import androidx.annotation.DrawableRes
import com.example.devfesttttt.R

object OnBoardingData {
    fun getItems(): List<OnBoardingItem> {
        return listOf(
            OnBoardingItem(
                R.mipmap.ic_launcher_foreground,
                "Welcome To Devfest"),
            OnBoardingItem(
                R.mipmap.ic_launcher_foreground,
//                R.drawable.ic_dev_fest,
                "We are glad to have you here at the conference"),
            OnBoardingItem(null,
                "I sha know you came for the swag and to meet celeb")
        )
    }

    data class OnBoardingItem(
        @DrawableRes val image: Int?,
        val text: String
    )
}