package com.tenutz.storemngsim.data.datasource.api.dto.option

import okhttp3.MultipartBody

data class OptionCreateRequest(
    val image: MultipartBody.Part?,
    val optionCode: String,
    val optionName: String,
    val price: Int,
    val discountedPrice: Int?,
    val additionalPackagingPrice: Int?,
    val packaging: String,
    val outOfStock: Boolean,
    val use: Boolean,
    val optionNameKor: String? = null,
    val showDateFrom: String? = null,
    val showDateTo: String? = null,
    val showTimeFrom: String? = null,
    val showTimeTo: String? = null,
    val showDayOfWeek: String? = null,
    val eventDateFrom: String? = null,
    val eventDateTo: String? = null,
    val eventTimeFrom: String? = null,
    val eventTimeTo: String? = null,
    val eventDayOfWeek: String? = null,
)
