/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.devfesttttt.presentation.authentication.ui

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
                    Intent(this, AuthenticationActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                )
            }
        }
    }

    companion object {

        private const val START_ACTIVITY_PATH = "/start-activity"
        const val IMAGE_PATH = "/image"
        const val EMAIL_PATH = "/email"
        const val IMAGE_KEY = "photo"
        const val EMAIL_KEY = "email"
    }
}
