package com.tenutz.storemngsim.data.datasource.api.dto.option

import okhttp3.MultipartBody

data class OptionCreateRequest(
    val image: MultipartBody.Part? = null,
    val optionCode: String,
    val optionName: String,
    val price: Int,
    val use: Boolean,
)
