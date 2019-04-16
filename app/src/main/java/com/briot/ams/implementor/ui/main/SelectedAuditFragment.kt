package com.briot.ams.implementor.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.briot.ams.implementor.MainActivity

import com.briot.ams.implementor.R
import com.briot.ams.implementor.repository.remote.Asset
import com.pascalwelsch.arrayadapter.ArrayAdapter
import io.github.pierry.progress.Progress
import kotlinx.android.synthetic.main.asset_details_scan_fragment.*
import kotlinx.android.synthetic.main.asset_item_list_row.view.*
import kotlinx.android.synthetic.main.selected_audit_fragment.*

class SelectedAuditFragment : Fragment() {

    companion object {
        fun newInstance() = SelectedAuditFragment()
    }

    private lateinit var viewModel: SelectedAuditViewModel
    private var progress: Progress? = null
    private var oldResponse: List<Asset>? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.selected_audit_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SelectedAuditViewModel::class.java)
        (this.activity as AppCompatActivity).setTitle("Selected Audit")

        pendingAuditAssetLists.adapter = SelectedAuditAssetsAdapter(this.context!!)
        pendingAuditAssetLists.layoutManager = LinearLayoutManager(this.context)


        viewModel.pendingAuditAssetsList.observe(this, Observer<List<Asset>> {
            MainActivity.hideProgress(this.progress)
            this.progress = null

            if (it != null && it != oldResponse) {
                for(i in 0 until it.size) {
                    (pendingAuditAssetLists.adapter as AssetItemsAdapter).add(it[i])
                }
                pendingAuditAssetLists.adapter.notifyDataSetChanged()
            }

            oldResponse = it
        })

        viewModel.networkError.observe(this, Observer<Boolean> {
            if (it == true) {
                MainActivity.hideProgress(this.progress)
                this.progress = null

                MainActivity.showAlert(this.activity as AppCompatActivity, "Server is not reachable, please check if your internet connection is working");
            }
        })

        scanLocationButton.setOnClickListener {
            this.progress = MainActivity.showProgressIndicator(this.activity as AppCompatActivity, "Please wait")
//            viewModel.loadAssetDetails(assetScanText.text.toString())
        }

        submitAudit.setOnClickListener {
            this.progress = MainActivity.showProgressIndicator(this.activity as AppCompatActivity, "Please wait")
//            viewModel.loadAssetDetails(assetScanText.text.toString())
        }

//        submitAudit.setOnEditorActionListener { _, i, keyEvent ->
//            var handled = false
//            if (i == EditorInfo.IME_ACTION_DONE || (keyEvent.keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.action == KeyEvent.ACTION_DOWN)) {
//                this.progress = MainActivity.showProgressIndicator(this.activity as AppCompatActivity, "Please wait")
////                viewModel.loadAssetDetails(assetScanText.text.toString())
//
//                handled = true
//            }
//            handled
//        }

    }

}


class SelectedAuditAssetsAdapter(val context: Context) : ArrayAdapter<Asset, SelectedAuditAssetsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val assetItemHeadingId: TextView
        val assetItemValueId: TextView

        init {
            assetItemHeadingId = itemView.assetItemHeadingId as TextView
            assetItemValueId = itemView.assetItemTextId as TextView
        }
    }

    override fun getItemId(item: Asset): Any {
        return item
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position) as Asset
//        var displayItem: String = item.id.toString() + " - " + item.createdAt.toString()
//        holder.auditlistId.setText(displayItem)
//
//        holder.auditlistId.setOnClickListener {
//            PrefRepository.singleInstance.setKeyValue(PrefConstants().PENDINGAUDITLISTID,item.id.toString())
//            Navigation.findNavController(it).navigate(R.id.action_auditListFragment_to_SelectedAuditFragment)
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
                .inflate(R.layout.asset_item_list_row, parent, false)
        return ViewHolder(view)
    }
}
