package com.tenutz.storemngsim.data.datasource.api.dto.category

import java.util.*

data class MainCategoriesResponse(
    val mainCategories: List<MainCategory>,
) {
    data class MainCategory(
        val storeCode: String?,
        val categoryCode: String?,
        val categoryName: String?,
        val use: Boolean,
        val order: Int?,
        val createdAt: Date?,
        val lastModifiedAt: Date?,
    )
}