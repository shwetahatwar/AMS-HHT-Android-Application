package com.briot.ams.implementor.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel;
import android.util.Log
import com.briot.ams.implementor.repository.remote.Asset
import com.briot.ams.implementor.repository.remote.Audit
import com.briot.ams.implementor.repository.remote.RemoteRepository
import java.net.SocketException
import java.net.SocketTimeoutException

class SelectedAuditViewModel : ViewModel() {

    val TAG = "SelectedAuditViewModel"
    val pendingAudit: LiveData<Audit> =  MutableLiveData<Audit>()

    val pendingAuditAssetsList: LiveData<List<Asset>> = MutableLiveData<List<Asset>>()

    val networkError: LiveData<Boolean> = MutableLiveData<Boolean>()

    fun pendingAuditList(auditId: String) {
        (networkError as MutableLiveData<Boolean>).value = false
        RemoteRepository.singleInstance.pendingAuditAssetsList(auditId, this::handleResponse, this::handleError)
    }

    private fun handleResponse(item: List<Asset>) {
        Log.d(TAG, "successful" + item.toString())
        (this.pendingAuditAssetsList as MutableLiveData<List<Asset>>).value = item
    }


    private fun handleError(error: Throwable) {
        Log.d(TAG, error.localizedMessage)

        if (error is SocketException || error is SocketTimeoutException) {
        (networkError as MutableLiveData<Boolean>).value = true
        }
    }
}
