package com.epsports.alexamart.dashboard.seller.upload

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.epsports.alexamart.core.DataState
import com.epsports.alexamart.data.Product
import com.epsports.alexamart.data.repository.SellerRepository
import javax.inject.Inject

class UploadProductViewmodel @Inject constructor(private val repo: SellerRepository) : ViewModel() {
    private val _productUploadResponse = MutableLiveData<DataState<String>>()
    val productUploadResponse: LiveData<DataState<String>> = _productUploadResponse

    fun productUpload(product: Product) {
        _productUploadResponse.postValue(DataState.Loading())

        val imageUri: Uri = product.imageLink.toUri()

        repo.uploadProductImage(imageUri).addOnSuccessListener { snapshot ->
            snapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener { url ->



                product.imageLink = url.toString()
                repo.uploadProduct(product).addOnSuccessListener {
                    _productUploadResponse.postValue(DataState.Success("Uploaded and update product successfully"))
                }.addOnFailureListener {
                    _productUploadResponse.postValue(DataState.Error("${it.message}"))
                }

            }

        }.addOnFailureListener {
            _productUploadResponse.postValue(DataState.Error("Image Upload Fail"))
        }
    }


    private val _selectedImageUri = MutableLiveData<Uri?>()
    val selectedImageUri: LiveData<Uri?> = _selectedImageUri

    fun setImageUri(uri: Uri) {
        _selectedImageUri.value = uri
    }

    fun clearImageUri() {
        _selectedImageUri.value = null
    }
}