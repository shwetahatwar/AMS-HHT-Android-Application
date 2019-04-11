package com.example.manishraje.implementor.repository.remote

import com.example.manishraje.implementor.RetrofitHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RemoteRepository {
    companion object {
        val singleInstance = RemoteRepository();
    }

    fun loginUser(username: String, password: String, handleResponse: (User) -> Unit, handleError: (Throwable) -> Unit) {
        var signInRequest: SignInRequest = SignInRequest();
        signInRequest.username = username;
        signInRequest.password = password;
        RetrofitHelper.retrofit.create(ApiInterface::class.java)
                .login(signInRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(handleResponse, handleError)
    }

    fun getProductDetails(barcode: String, handleResponse: (Product) -> Unit, handleError: (Throwable) -> Unit) {
        RetrofitHelper.retrofit.create(ApiInterface::class.java)
                .productDetails(barcode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(handleResponse, handleError)
    }

    fun getLocationDetails(rackID: String, handleResponse: (RackLocation) -> Unit, handleError: (Throwable) -> Unit) {
        RetrofitHelper.retrofit.create(ApiInterface::class.java)
                .locationDetails(rackID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(handleResponse, handleError)
    }

    fun putAwaySubmit(rackid: String, username: String, materialid: String, handleResponse: (Product) -> Unit, handleError: (Throwable) -> Unit) {
        RetrofitHelper.retrofit.create(ApiInterface::class.java)
                .putAwaySubmit(rackid, username, materialid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(handleResponse, handleError)
    }

    fun pendingPicklists(handleResponse: (List<PendingPicklist>) -> Unit, handleError: (Throwable) -> Unit) {
        RetrofitHelper.retrofit.create(ApiInterface::class.java)
                .pendingPicklists()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(handleResponse, handleError)
    }

    fun picklist(PicklistID: String, handleResponse: (List<PendingPicklist>) -> Unit, handleError: (Throwable) -> Unit){
        RetrofitHelper.retrofit.create(ApiInterface::class.java)
                .picklist(PicklistID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(handleResponse, handleError)
    }

    fun picklistProductSubmit(materialid: String, picklistId: String, handleResponse: (Product) -> Unit, handleError: (Throwable) -> Unit) {
        RetrofitHelper.retrofit.create(ApiInterface::class.java)
                .picklistProductSubmit(materialid, picklistId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(handleResponse, handleError)
    }

    fun completePicklistProduct(PicklistID: String, handleResponse: (PendingPicklist) -> Unit, handleError: (Throwable) -> Unit) {
        RetrofitHelper.retrofit.create(ApiInterface::class.java)
                .completePicklistProduct(PicklistID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(handleResponse, handleError)
    }

    fun putAwayReport(username: String, handleResponse: (List<Product>) -> Unit, handleError: (Throwable) -> Unit) {
        RetrofitHelper.retrofit.create(ApiInterface::class.java)
                .putAwayReport(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(handleResponse, handleError)
    }
}