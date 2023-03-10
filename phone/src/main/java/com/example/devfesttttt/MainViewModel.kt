package com.example.devfesttttt

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.devfesttttt.MainActivity.Companion.PAIRING_COMPLETE_KEY
import com.example.devfesttttt.MainActivity.Companion.PAIRING_COMPLETE_PATH
import com.google.android.gms.wearable.*

class MainViewModel :
    ViewModel(),
    DataClient.OnDataChangedListener{

    var image by mutableStateOf<Bitmap?>(null)
        private set

    var name by mutableStateOf<String?>(null)
        private set

    var email by mutableStateOf<String?>(null)
        private set

    var isSignedIn by mutableStateOf(false)
        private set

    var pairingComplete by mutableStateOf(false)
        private set

    override fun onDataChanged(dataEvents: DataEventBuffer) {

        dataEvents.forEach { dataEvent ->
            when (dataEvent.type) {
                DataEvent.TYPE_CHANGED -> {
                    when (dataEvent.dataItem.uri.path) {
                        PAIRING_COMPLETE_PATH -> {
                            pairingComplete = DataMapItem.fromDataItem(dataEvent.dataItem)
                                .dataMap
                                .getBoolean(PAIRING_COMPLETE_KEY)
                            Log.d("Pairing Complete: ", pairingComplete.toString())
                        }
                    }
                }
            }
        }
    }

    fun onPictureTaken(bitmap: Bitmap?) {
        image = bitmap ?: return
    }

    fun saveName(newName: String?) {
        name = newName ?: return
    }

    fun saveEmail(newEmail: String?) {
        email = newEmail ?: return
    }

    fun setSignInStatus(newIsSignedIn: Boolean) {
        isSignedIn = newIsSignedIn
    }
}