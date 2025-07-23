package com.epsports.alexamart.dashboard.seller.upload

import android.Manifest
import android.app.Activity
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.epsports.alexamart.base.BaseFragment
import com.epsports.alexamart.core.DataState
import com.epsports.alexamart.core.areAllPermissionGranted
import com.epsports.alexamart.core.extract
import com.epsports.alexamart.core.requestPermission
import com.epsports.alexamart.data.Product
import com.epsports.alexamart.databinding.FragmentUploadProductBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID


@AndroidEntryPoint
class UploadProductFragment :
    BaseFragment<FragmentUploadProductBinding>(FragmentUploadProductBinding::inflate) {
    private val viewmodel: UploadProductViewmodel by viewModels()
    private lateinit var permissionRequest: ActivityResultLauncher<Array<String>>
    private val product: Product by lazy {
        Product()
    }

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

                FirebaseAuth.getInstance().currentUser?.let {
                    product.apply {
                        this.productId = UUID.randomUUID().toString()
                        this.sellerId = it.uid
                        this.name = name
                        this.price = price.toDouble()
                        this.description = description
                        this.amount = amount.toInt()
                    }
                }



                uploadProduct(product)
            }

        }

        observeImageUri()
    }

    private fun observeImageUri() {
        viewmodel.selectedImageUri.observe(viewLifecycleOwner) {
            it?.let {
                binding.ivProduct.setImageURI(it)
            }
        }
    }


    private fun getPermissionRequest(): ActivityResultLauncher<Array<String>> {
        return registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (areAllPermissionGranted(permissionList)) {
                ImagePicker.with(this)
                    .crop()
                    .compress(1024)         //Final image size will be less than 1 MB(Optional)
                    .maxResultSize(
                        512,
                        512
                    )  //Final image resolution will be less than 1080 x 1080(Optional)
                    .createIntent { intent ->
                        startForProfileImageResult.launch(intent)
                    }
                Toast.makeText(requireContext(), "Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Not Granted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun uploadProduct(product: Product) {
        viewmodel.productUpload(product)
    }

    override fun allObserver() {
        viewmodel.productUploadResponse.observe(viewLifecycleOwner) {

            when (it) {
                is DataState.Error -> {
                    loading.dismiss()
                }

                is DataState.Loading -> {
                    loading.show()
                }

                is DataState.Success -> {
                    loading.dismiss()
                    Toast.makeText(requireContext(), it.data, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {

        private val permissionList = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        )
    }


    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data!!
                viewmodel.setImageUri(fileUri)
                product.imageLink = fileUri.toString()

            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
}