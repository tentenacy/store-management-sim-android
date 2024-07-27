package com.tenutz.storemngsim.data.datasource.paging.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class SalesList(
    val total: Int = 0,
    val page: Int = 0,
    val salesList: List<Sales>
): Parcelable {

    @IgnoredOnParcel
    val endOfPage = total - 1 <= page

    @Parcelize
    @Entity(tableName = "sales")
    data class Sales(
        @PrimaryKey val id: Long,
        val storeCode: String?,
        val orderNumber: String?,
        val orderType: String?,
        val orderedAt: Date?,
        val posNumber: String?,
        val approvalType: String?,
        val paymentType: String?,
        val paymentAmount: Int,
        val saleNumber: String?,
        val approvalNumber: String?,
        val creditCardCompanyName: String?,
    ): Parcelable

    @Parcelize
    @Entity(tableName = "sales_remote_keys")
    data class SalesRemoteKeys(
        @PrimaryKey(autoGenerate = true) val id: Long,
        val prevKey: Int?,
        val nextKey: Int,
    ): Parcelable
}