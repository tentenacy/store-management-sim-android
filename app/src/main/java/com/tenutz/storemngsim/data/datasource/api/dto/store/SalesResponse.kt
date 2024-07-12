package com.tenutz.storemngsim.data.datasource.api.dto.store

data class SalesResponse(
    val storeCode: String?,
    val orderNumber: String?,
    val orderType: String?,
    val orderedAt: String?,
    val posNumber: String?,
    val approvalType: String?,
    val paymentType: String?,
    val paymentAmount: Int,
    val saleNumber: String?,
    val approvalNumber: String?,
    val creditCardCompanyName: String?,
)
