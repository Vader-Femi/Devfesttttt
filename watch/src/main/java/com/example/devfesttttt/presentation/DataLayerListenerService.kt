package com.example.devfesttttt.presentation

import android.content.Intent
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService

@OptIn(ExperimentalPagerApi::class)
class DataLayerListenerService : WearableListenerService() {

    override fun onMessageReceived(messageEvent: MessageEvent) {
        super.onMessageReceived(messageEvent)

        when (messageEvent.path) {
            START_ACTIVITY_PATH -> {
                startActivity(
                    Intent(this, MainActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                )
            }
        }
    }

    companion object {

        private const val START_ACTIVITY_PATH = "/start-activity"
        const val IMAGE_PATH = "/image"
        const val EMAIL_PATH = "/email"
        const val NAME_PATH = "/name"
        const val IMAGE_KEY = "photo"
        const val EMAIL_KEY = "email"
        const val NAME_KEY = "name"
    }
}
