package com.tenutz.storemngsim.data.datasource.api.dto.category

import java.util.*

data class MainCategoryResponse(
    val storeCode: String?,
    val categoryCode: String?,
    val categoryName: String?,
    val use: Boolean,
    val order: Int?,
    val creator: String?,
    val createdAt: Date?,
    val lastModifier: String?,
    val lastModifiedAt: Date?,
)
