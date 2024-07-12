package com.tenutz.storemngsim.data.datasource.api.dto.category

import java.util.*

data class MiddleCategoriesResponse(
    val middleCategories: List<MiddleCategory>?
) {
    data class MiddleCategory(
        val storeCode: String?,
        val mainCategoryCode: String?,
        val categoryCode: String?,
        val categoryName: String?,
        val use: Boolean,
        val imageName: String?,
        val imageUrl: String?,
        val order: Int?,
        val createdAt: Date?,
        val lastModifiedAt: Date?,
    )
}
