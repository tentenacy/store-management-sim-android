package com.tenutz.storemngsim.data.datasource.api.dto.category

import java.util.*

data class SubCategoriesResponse(
    val subCategories: List<SubCategory>,
) {
    data class SubCategory(
        val storeCode: String?,
        val mainCategoryCode: String?,
        val middleCategoryCode: String?,
        val categoryCode: String?,
        val categoryName: String?,
        val use: Boolean,
        val order: Int?,
        val createdAt: Date?,
        val lastModifiedAt: Date?,
    )
}
