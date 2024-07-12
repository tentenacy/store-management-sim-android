package com.tenutz.storemngsim.data.datasource.api.dto.store

data class SalesRequest(
    val paymentType: String? = null,
    val approvalType: String? = null,
    val orderType: String? = null,
)
