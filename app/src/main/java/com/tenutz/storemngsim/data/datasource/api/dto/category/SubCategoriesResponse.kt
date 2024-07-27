package com.tenutz.storemngsim.data.datasource.api.dto.category

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class SubCategoriesResponse(
    val subCategories: List<SubCategory>,
    val middleCategory: MiddleCategoryResponse,
): Parcelable {
    @Parcelize
    data class SubCategory(
        val storeCode: String,
        val mainCategoryCode: String,
        val middleCategoryCode: String,
        val categoryCode: String,
        val categoryName: String?,
        val use: Boolean,
        val order: Int?,
        val createdAt: Date?,
        val lastModifiedAt: Date?,
    ): Parcelable
}
