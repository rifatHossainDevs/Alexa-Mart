package com.epsports.alexamart.dashboard.seller.upload

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UploadProductViewmodel: ViewModel() {
    private val _selectedImageUri = MutableLiveData<Uri?>()
    val selectedImageUri: LiveData<Uri?> get() = _selectedImageUri

    fun setImageUri(uri: Uri){
        _selectedImageUri.value = uri
    }

    fun clearImageUri(){
        _selectedImageUri.value = null
    }
}