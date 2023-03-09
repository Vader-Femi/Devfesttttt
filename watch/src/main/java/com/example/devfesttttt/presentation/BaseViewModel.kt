package com.example.devfesttttt.presentation

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor(
    private val repository: BaseRepositoryImpl,
    private val application: Application,
) : ViewModel() {

    fun userEmail(email: String) = viewModelScope.launch {
        repository.userEmail(email)
    }

    suspend fun userEmail(): String = repository.userEmail()

    fun userName(name: String) = viewModelScope.launch {
        repository.userName(name)
    }

    suspend fun userName(): String = repository.userName()

    suspend fun userSignedIn(): Boolean = repository.userSignedIn()

    fun userSignedIn(userSignedIn: Boolean) = viewModelScope.launch {
        if (!userSignedIn){
            deleteUserDetails()
        }
        repository.userSignedIn(userSignedIn)
    }

    private fun deleteUserDetails(){
        userName("")
        userEmail("")
        deleteProfileImage()
    }


    fun profileImage(bmp: Bitmap) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                application.openFileOutput(PROFILE_PIC, Context.MODE_PRIVATE).use { stream ->
                    if (!bmp.compress(Bitmap.CompressFormat.PNG, 100, stream)) {
                        throw IOException("Couldn't Save Image")
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
                Log.e("Saving Profile Failed", e.message.toString())
            }
        }
    }

    fun profileImage(): Bitmap? {
        val imageFile = File(application.filesDir, PROFILE_PIC)
        return BitmapFactory.decodeFile(imageFile.path)
    }

    private fun deleteProfileImage() {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                application.deleteFile(PROFILE_PIC)
            } catch (e: IOException) {
                e.printStackTrace()
                Log.e("Deleting Profile Failed", e.message.toString())
            }
        }
    }

    companion object{
        const val PROFILE_PIC = "PROFILE_PIC"
    }
}
