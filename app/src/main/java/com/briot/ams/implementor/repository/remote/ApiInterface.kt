package com.briot.ams.implementor.repository.remote

import io.reactivex.Observable
import retrofit2.http.*
import java.util.*


class SignInRequest {
    var username: String? = null
    var password: String? = null
}

class User {
    var username: String? = null
    var token: String? = null
}

class Site {
    var id: Int = -1
    var name: String? = null
    var status: String? = null
}

class Location {
    var id: Int = -1
    var name: String? = null
    var siteId: Int = -1
    var status: String? = null
}

class SubLocation {
    var id: Int = -1
    var name: String? = null
    var locationId: Int = -1
    var statusId: String? = null
    var barcodeSerial: String? = null
}

class Audit {
    var id: Int = -1
    var status: String? = null
    var site: Int = -1
    var location: Int = -1
    var subLocation: Int = -1
    var auditedBy: String? = null
    var auditedAt: Date? = null
    var approvedBy: String? = null
    var approvedAt: Date? = null
    var createdAt: Date? = null
    var updatedAt: Date? = null
//    var site: Site? = null
//    var location: Location? = null
//    var SubLocation: SubLocation? = null
}

class Asset {
    var id: Int = -1
    var oemSerialNumber: String? = null
    var assetSubTypeId: Int = -1
    var assetSubType: String? = null
    var assetDescription: String? = null
    var manufacturerId: Int = -1
    var manufacturer: String? = null
    var modelId: Int = -1
    var model: String? = null
    var siteId: String? = null
    var locationId: String? = null
    var subLocationId: String? = null
    var site: String? = null
    var location: String? = null
    var subLocation: String? = null
    var employeeId: String? = null
    var departmentId: String? = null
    var stateId: Int = -1
    var state: String? = null
    var costcenterId: String? = null
    var status: String? = null
    var barcodeSerial: String? = null
    var poDate: String? = null
    var costOfAsset: Int = 0
    var poNumber: String? = null
    var depreciationTypeId: Int = -1
    var rateOfDepreciation: String? = null
    var lifeExpectancy: String? = null
    var calculatedBook: Int = 0
    var asOfDateDepreciation: String? = null
    var purchaseDate: String? = null
    var createdAt: Date? = null
    var updatedAt: Date? = null
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

    @GET("auditMaster/audit/auditPending")
    fun pendingAuditList() : Observable<List<Audit>>

    @GET("asset/{barcode}")
    fun assetDetails(@Path("barcode") barcode: String) : Observable<Asset>
}