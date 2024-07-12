package com.tenutz.storemngsim.data.datasource.api.dto.category

import okhttp3.MultipartBody

data class MiddleCategoryCreateRequest(
    val image: MultipartBody.Part? = null,
    val categoryCode: String,
    val categoryName: String,
    val use: Boolean,
    val businessNumber: String? = null,
    val representativeName: String? = null,
    val tel: String? = null,
    val address: String? = null,
    val tid: String? = null,
)
