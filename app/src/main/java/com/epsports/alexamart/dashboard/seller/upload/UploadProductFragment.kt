package com.epsports.alexamart.dashboard.seller.upload

import com.epsports.alexamart.base.BaseFragment
import com.epsports.alexamart.core.extract
import com.epsports.alexamart.data.Product
import com.epsports.alexamart.databinding.FragmentUploadProductBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UploadProductFragment :
    BaseFragment<FragmentUploadProductBinding>(FragmentUploadProductBinding::inflate) {
    override fun setAllClickListener() {
        binding.apply {

            ivProduct.setOnClickListener {

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

    private fun uploadProduct(product: Product) {

    }

    override fun allObserver() {

    }


}