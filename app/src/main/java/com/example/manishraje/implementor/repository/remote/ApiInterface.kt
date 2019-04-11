package com.example.manishraje.implementor.repository.remote

import io.reactivex.Observable
import retrofit2.http.*


class SignInRequest {
    var username: String? = null
    var password: String? = null
}

class User {
    var username: String? = null
    var token: String? = null
}

class Product {
    var BarcodeSerialNumber: String? = null
    var ProductStockId: String? = null
    var ProjectId: String? = null
    var ItemId: String? = null
    var ProductDetailId: String? = null
    var TransactionSerial: String? = null
    var ProjectName: String? = null
    var TransactionDate: String? = null
    var AccountName: String? = null
    var ItemName: String? = null
    var ProductName: String? = null
    var Quantity: Double? = null
    var UnitName: String? = null
    var RackId: String? = null
}

class RackLocation {
    var rackID: String? = null
    var location: String? = null
    var sublocation: String? = null
    var description: String? = null
}

class PendingPicklist {
    var PicklistID: String? = null
    var Barcode:String? = null
}


class PutAwaySubmission {

}

interface ApiInterface {
    @POST("users/sign_in")
    fun login(@Body signInRequest: SignInRequest): Observable<User>

    @GET("material")
    fun productDetails(@Query("barcode") barcode: String): Observable<Product>

    @POST("location")
    fun locationDetails(@Query("rackID") rackID: String): Observable<RackLocation>

    @GET("putaway")
    fun putAwaySubmit(@Query("rackid") rackid: String, @Query("username") username: String, @Query("materialid") materialid: String): Observable<Product>

    @GET("pendingpicklists")
    fun pendingPicklists() : Observable<List<PendingPicklist>>

    @GET("picklist")
    fun  picklist(@Query("picklistId") picklistId: String) : Observable<List<PendingPicklist>>

    @GET("picklistProductSubmit")
    fun  picklistProductSubmit(@Query("materialid") materialid: String, @Query("picklistId") picklistId: String) : Observable<Product>

    @GET("completePicklistProduct")
    fun  completePicklistProduct(@Query("picklistId") picklistId: String) : Observable<PendingPicklist>

    @GET("putawayreport")
    fun putAwayReport(@Query("username") username: String): Observable<List<Product>>

}