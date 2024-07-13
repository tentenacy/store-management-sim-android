package com.tenutz.storemngsim.ui.menu.category.middle.args

import android.os.Parcelable
import com.tenutz.storemngsim.data.datasource.api.dto.category.MiddleCategoriesResponse
import java.util.*

data class MiddleCategoriesEditArgs(
    val middleCategoriesEdit: List<MiddleCategoryEdit>
) {
    data class MiddleCategoryEdit(
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
        var checked: Boolean = false,
    )
}