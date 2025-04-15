package com.epsports.alexamart.dashboard.seller.upload

import android.Manifest
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.epsports.alexamart.base.BaseFragment
import com.epsports.alexamart.core.areAllPermissionGranted
import com.epsports.alexamart.core.extract
import com.epsports.alexamart.core.requestPermission
import com.epsports.alexamart.data.Product
import com.epsports.alexamart.databinding.FragmentUploadProductBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UploadProductFragment :
    BaseFragment<FragmentUploadProductBinding>(FragmentUploadProductBinding::inflate) {
    override fun setAllClickListener() {

        permissionRequest = getPermissionRequest()

        binding.apply {



            ivProduct.setOnClickListener {
                requestPermission(permissionRequest, permissionList)
            }

            btnUploadProduct.setOnClickListener {
                val name = etProductName.extract()
                val price = etProductPrice.extract()
                val description = etProductDescription.extract()
                val amount = etProductAmount.extract()

                val product = Product(
                    name = name,
                    price = price.toDouble(),
                    description = description,
                    amount = amount.toInt()
                )

                uploadProduct(product)
            }

        }
    }

    private fun getPermissionRequest(): ActivityResultLauncher<Array<String>> {
        return registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (areAllPermissionGranted(permissionList)){
                Toast.makeText(requireContext(), "Granted", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireContext(), "Not Granted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun uploadProduct(product: Product) {

    }

    override fun allObserver() {

    }


    companion object {
        private val permissionList = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        )
    }

    private lateinit var permissionRequest: ActivityResultLauncher<Array<String>>

}