package com.tenutz.storemngsim.data.datasource.api.dto.category

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class MiddleCategoriesResponse(
    val middleCategories: List<MiddleCategory>
): Parcelable {
    @Parcelize
    data class MiddleCategory(
        val storeCode: String?,
        val mainCategoryCode: String?,
        val categoryCode: String?,
        val categoryName: String?,
        val use: Boolean,
        val imageName: String?,
        val imageUrl: String?,
        val order: Int?,
        val tel: String?,
        val address: String?,
        val createdAt: Date?,
        val lastModifiedAt: Date?,
    ): Parcelable
}
