package com.tenutz.storemngsim.data.datasource.api.dto.store

data class StoreMainResponse(
    val siteCode: String?,
    val storeCode: String?,
    val storeName: String?,
    val storeManagerName: String?,
    val todaySalesAmountTotal: Int?,
    val yesterdaySalesAmountTotal: Int?,
    val thisMonthSalesAmountTotal: Int?,
)