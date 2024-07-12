package com.tenutz.storemngsim.data.datasource.api.dto.category

data class SubCategoryCreateRequest(
    val categoryCode: String,
    val categoryName: String,
    val use: Boolean,
)
