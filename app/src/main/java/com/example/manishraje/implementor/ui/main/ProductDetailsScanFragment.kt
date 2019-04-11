package com.example.manishraje.implementor.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.navigation.Navigation
import com.example.manishraje.implementor.MainActivity
import com.example.manishraje.implementor.R
import com.example.manishraje.implementor.repository.local.PrefConstants
import com.example.manishraje.implementor.repository.local.PrefRepository
import com.example.manishraje.implementor.repository.remote.Product
import io.github.pierry.progress.Progress
import kotlinx.android.synthetic.main.product_details_scan_fragment.*


class ProductDetailsScanFragment : Fragment() {

    companion object {
        fun newInstance() = ProductDetailsScanFragment()
    }

    private lateinit var viewModel: ProductDetailsScanViewModel
    private var progress: Progress? = null
    private var oldProduct: Product? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.product_details_scan_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ProductDetailsScanViewModel::class.java)
        // TODO: Use the ViewModel

        (this.activity as AppCompatActivity).setTitle("Asset Details")

        viewModel.product.observe(this, Observer<Product> {
            MainActivity.hideProgress(this.progress)
            this.progress = null

            if (it != null && it != oldProduct) {
                if (it != viewModel.invalidProduct) {
                    val prefConstants: PrefConstants = PrefConstants()
                    PrefRepository.singleInstance.setKeyValue(prefConstants.PRODUCT_ACCOUNTNAME, it.AccountName ?: "NA")
                    PrefRepository.singleInstance.setKeyValue(prefConstants.PRODUCT_PRODUCTNAME, it.ProductName ?: "NA")
                    PrefRepository.singleInstance.setKeyValue(prefConstants.PRODUCT_ITEMNAME, it.ItemName ?: "NA")
                    PrefRepository.singleInstance.setKeyValue(prefConstants.PRODUCT_QUANTITY, "" + (it.Quantity ?: "NA"))
                    PrefRepository.singleInstance.setKeyValue(prefConstants.PRODUCT_UNITNAME, it.UnitName ?: "NA")
                    PrefRepository.singleInstance.setKeyValue(prefConstants.PRODUCT_DETAILS_ID, it.ProductDetailId ?: "NA")
                    PrefRepository.singleInstance.setKeyValue(prefConstants.PRODUCT_STOCK_ID, it.ProductStockId ?: "NA")
                    PrefRepository.singleInstance.setKeyValue(prefConstants.PROJECT_ID, it.ProjectId ?: "NA")
                    PrefRepository.singleInstance.setKeyValue(prefConstants.PROJECT_NAME, it.ProjectName ?: "NA")
                    PrefRepository.singleInstance.setKeyValue(prefConstants.PRODUCT_ITEM_ID, it.ItemId ?: "NA")
                    Navigation.findNavController(viewProductDetails).navigate(R.id.action_productDetailsScanFragment_to_produceDetailsFragment)
                } else {

                }
            }

            oldProduct = it
        })

        viewModel.networkError.observe(this, Observer<Boolean> {
            if (it == true) {
                MainActivity.hideProgress(this.progress)
                this.progress = null

                MainActivity.showAlert(this.activity as AppCompatActivity, "Server is not reachable, please check if your internet connection is working");
            }
        })

        viewProductDetails.setOnClickListener {
            this.progress = MainActivity.showProgressIndicator(this.activity as AppCompatActivity, "Please wait")
            viewModel.loadProductDetails(productScanText.text.toString())
        }

        productScanText.setOnEditorActionListener { textView, i, keyEvent ->
            var handled = false
            if (i == EditorInfo.IME_ACTION_DONE || (keyEvent.keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.action == KeyEvent.ACTION_DOWN)) {
                this.progress = MainActivity.showProgressIndicator(this.activity as AppCompatActivity, "Please wait")
                viewModel.loadProductDetails(productScanText.text.toString())

                handled = true
            }
            handled
        }
    }

}
