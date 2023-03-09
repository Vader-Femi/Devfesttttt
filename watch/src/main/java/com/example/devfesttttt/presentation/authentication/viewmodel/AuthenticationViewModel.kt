package com.example.devfesttttt.presentation.authentication.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.devfesttttt.presentation.BaseViewModel
import com.example.devfesttttt.presentation.authentication.repository.AuthenticationRepositoryImpl
import com.example.devfesttttt.presentation.DataLayerListenerService
import com.google.android.gms.wearable.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthenticationViewModel @Inject constructor(
    repository: AuthenticationRepositoryImpl,
    private val application: Application
) : BaseViewModel(repository, application),
    DataClient.OnDataChangedListener {

    var image by mutableStateOf<Bitmap?>(null)
        private set

    var email by mutableStateOf<String?>(null)
        private set

    var name by mutableStateOf<String?>(null)
        private set

    private var loadPhotoJob: Job = Job().apply { complete() }
    override fun onDataChanged(dataEvents: DataEventBuffer) {

        dataEvents.forEach { dataEvent ->
            when (dataEvent.type) {
                DataEvent.TYPE_CHANGED -> {
                    when (dataEvent.dataItem.uri.path) {
                        DataLayerListenerService.EMAIL_PATH -> {
                            email = DataMapItem.fromDataItem(dataEvent.dataItem)
                                .dataMap
                                .getString(DataLayerListenerService.EMAIL_KEY)
                            Log.d("EmailSending", email.toString())
                        }
                        DataLayerListenerService.NAME_PATH -> {
                            name = DataMapItem.fromDataItem(dataEvent.dataItem)
                                .dataMap
                                .getString(DataLayerListenerService.NAME_KEY)
                            Log.d("NameSending", name.toString())
                        }
                        DataLayerListenerService.IMAGE_PATH -> {
                            loadPhotoJob.cancel()
                            loadPhotoJob = viewModelScope.launch {
                                image = loadBitmap(
                                    DataMapItem.fromDataItem(dataEvent.dataItem)
                                        .dataMap
                                        .getAsset(DataLayerListenerService.IMAGE_KEY)
                                )
                                Log.d("ImageSending", image.toString())
                            }
                        }
                    }
                }
            }
        }
    }

    private suspend fun loadBitmap(asset: Asset?): Bitmap? {
        if (asset == null) return null
        val response =
            Wearable.getDataClient(application).getFdForAsset(asset).await()
        return response.inputStream.use { inputStream ->
            withContext(Dispatchers.IO) {
                BitmapFactory.decodeStream(inputStream)
            }
        }
    }

}