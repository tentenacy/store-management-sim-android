package com.tenutz.storemngsim.data.datasource.api.dto.option

import okhttp3.MultipartBody

data class OptionUpdateRequest(
    val image: MultipartBody.Part? = null,
    val optionName: String,
    val price: Int,
    val use: Boolean?,
)
