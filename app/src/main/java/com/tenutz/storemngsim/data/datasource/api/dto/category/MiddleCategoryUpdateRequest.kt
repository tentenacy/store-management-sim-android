package com.tenutz.storemngsim.data.datasource.api.dto.category

import okhttp3.MultipartBody

data class MiddleCategoryUpdateRequest(
    val image: MultipartBody.Part?,
    val categoryName: String,
    val use: Boolean,
    val businessNumber: String?,
    val representativeName: String?,
    val tel: String?,
    val address: String?,
    val tid: String?,
)