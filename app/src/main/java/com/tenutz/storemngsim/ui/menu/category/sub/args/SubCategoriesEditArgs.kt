package com.tenutz.storemngsim.ui.menu.category.sub.args

import com.tenutz.storemngsim.data.datasource.api.dto.category.MiddleCategoryResponse
import java.util.Date

data class SubCategoriesEditArgs(
    val subCategoriesEdit: List<SubCategoryEdit>,
    val middleCategory: MiddleCategoryResponse,
) {
    data class SubCategoryEdit(
        val storeCode: String?,
        val mainCategoryCode: String?,
        val middleCategoryCode: String?,
        val categoryCode: String?,
        val categoryName: String?,
        val use: Boolean,
        val order: Int?,
        val createdAt: Date?,
        val lastModifiedAt: Date?,
        var checked: Boolean = false,
    )
}
