package com.tenutz.storemngsim.ui.menu.category.main.args

import java.util.*

data class MainCategoriesEditArgs(
    val mainCategoriesEdit: List<MainCategoryEdit>,
) {
    data class MainCategoryEdit(
        val storeCode: String?,
        val categoryCode: String?,
        val categoryName: String?,
        val use: Boolean,
        val order: Int?,
        val createdAt: Date?,
        val lastModifiedAt: Date?,
        var checked: Boolean = false,
    )
}