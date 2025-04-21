package com.epsports.alexamart.services

import android.net.Uri
import com.epsports.alexamart.data.Product
import com.epsports.alexamart.data.models.UserLogin
import com.epsports.alexamart.data.models.UserRegistration
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.storage.UploadTask

interface SellerService {

    fun uploadProductImage(productImageUri: Uri): UploadTask

    fun uploadProduct(product: Product):Task<Void>


}