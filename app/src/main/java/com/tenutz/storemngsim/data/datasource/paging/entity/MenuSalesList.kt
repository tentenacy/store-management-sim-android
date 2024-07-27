package com.tenutz.storemngsim.data.datasource.paging.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class MenuSalesList(
    val total: Int = 0,
    val page: Int = 0,
    val menuSalesList: List<MenuSales>,
): Parcelable {

    @IgnoredOnParcel
    val endOfPage = total - 1 <= page

    @Parcelize
    @Entity(tableName = "menu_sales")
    data class MenuSales(
        @PrimaryKey val id: Long,
        val soldAt: Date?,
        val menuName: String?,
        val categoryName: String?,
        val mainCategoryCode: String?,
        val middleCategoryCode: String?,
        val subCategoryCode: String?,
        val menuCode: String?,
        val cAuthAmount: Int,
        val cAuthCount: Int,
        val authAmount: Int,
        val authCount: Int,
        val authCAmount: Int,
        val authCCount: Int,
    ): Parcelable

    @Parcelize
    @Entity(tableName = "menu_sales_remote_keys")
    data class MenuSalesRemoteKeys(
        @PrimaryKey(autoGenerate = true) val id: Long,
        val prevKey: Int?,
        val nextKey: Int,
    ): Parcelable
}
