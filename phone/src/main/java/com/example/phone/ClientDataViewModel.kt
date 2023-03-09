package com.example.phone

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.phone.MainActivity.Companion.PAIRING_COMPLETE_KEY
import com.example.phone.MainActivity.Companion.PAIRING_COMPLETE_PATH
import com.google.android.gms.wearable.*

class ClientDataViewModel :
    ViewModel(),
    DataClient.OnDataChangedListener{

    var image by mutableStateOf<Bitmap?>(null)
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
}