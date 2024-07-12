package com.tenutz.storemngsim.data.datasource.api.dto.option

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
    val showDateFrom: String?,
    val showDateTo: String?,
    val showTimeFrom: String?,
    val showTimeTo: String?,
    val showDayOfWeek: String?,
    val eventDateFrom: String?,
    val eventDateTo: String?,
    val eventTimeFrom: String?,
    val eventTimeTo: String?,
    val eventDayOfWeek: String?,
)