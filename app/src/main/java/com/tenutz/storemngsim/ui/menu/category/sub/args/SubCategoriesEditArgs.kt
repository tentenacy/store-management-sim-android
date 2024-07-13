package com.tenutz.storemngsim.ui.menu.category.sub.args

import java.util.*

data class SubCategoriesEditArgs(
    val subCategoriesEdit: List<SubCategoryEdit>,
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
