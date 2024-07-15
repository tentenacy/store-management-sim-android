package com.tenutz.storemngsim.data.datasource.api.dto.option

import java.util.*

data class OptionResponse(
    val storeCode: String?,
    val optionCode: String?,
    val optionName: String?,
    val price: Int,
    val discountedPrice: Int,
    val additionalPackagingPrice: Int,
    val packaging: String?,
    val outOfStock: Boolean,
    val use: Boolean,
    val imageName: String?,
    val imageUrl: String?,
    val optionNameKor: String?,
    val showDateFrom: Date?,
    val showDateTo: Date?,
    val showTimeFrom: String?,
    val showTimeTo: String?,
    val showDayOfWeek: String?,
    val eventDateFrom: Date?,
    val eventDateTo: Date?,
    val eventTimeFrom: String?,
    val eventTimeTo: String?,
    val eventDayOfWeek: String?,
)
