package com.tenutz.storemngsim.data.datasource.api.dto.category

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.util.*


@Parcelize
data class MainCategoriesResponse(
    val mainCategories: @RawValue List<MainCategory>,
): Parcelable {
    @Parcelize
    data class MainCategory(
        val storeCode: String?,
        val categoryCode: String?,
        val categoryName: String?,
        val use: Boolean,
        val order: Int?,
        val createdAt: Date?,
        val lastModifiedAt: Date?,
    ): Parcelable
}