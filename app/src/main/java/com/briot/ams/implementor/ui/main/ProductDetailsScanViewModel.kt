package com.briot.ams.implementor.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel;
import android.util.Log
import com.briot.ams.implementor.repository.remote.RemoteRepository
import com.briot.ams.implementor.repository.remote.Product
import java.net.SocketException
import java.net.SocketTimeoutException

class ProductDetailsScanViewModel : ViewModel() {
    val TAG = "ProductScanViewModel"

    val product: LiveData<Product> = MutableLiveData<Product>()

    val networkError: LiveData<Boolean> = MutableLiveData<Boolean>()
    val invalidProduct: Product = Product()

    fun loadProductDetails(barcode: String) {
        (networkError as MutableLiveData<Boolean>).value = false
        RemoteRepository.singleInstance.getProductDetails(barcode, this::handleProductResponse, this::handleProductError)
    }

    private fun handleProductResponse(product: Product) {
        Log.d(TAG, "successful product" + product.toString())
        (this.product as MutableLiveData<Product>).value = product
    }

    private fun handleProductError(error: Throwable) {
        Log.d(TAG, error.localizedMessage)

        if (error is SocketException || error is SocketTimeoutException) {
            (networkError as MutableLiveData<Boolean>).value = true
        } else {
            (this.product as MutableLiveData<Product>).value = invalidProduct
        }
    }
    // TODO: Implement the ViewModel
}
