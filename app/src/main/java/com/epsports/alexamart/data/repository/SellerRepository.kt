package com.epsports.alexamart.data.repository

import android.net.Uri
import com.epsports.alexamart.core.Nodes
import com.epsports.alexamart.data.Product
import com.epsports.alexamart.services.SellerService
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import javax.inject.Inject

class SellerRepository @Inject constructor(
    private val db: FirebaseFirestore,
    private val storageRef: StorageReference,
) : SellerService {
    override fun uploadProductImage(productImageUri: Uri): UploadTask {
        val storage: StorageReference = storageRef.child("products").child("PRD_${System.currentTimeMillis()}")

        return storage.putFile(productImageUri)
    }

    override fun uploadProduct(product: Product):Task<Void> {
        return db.collection(Nodes.PRODUCT).document(product.productId).set(product)
    }


}