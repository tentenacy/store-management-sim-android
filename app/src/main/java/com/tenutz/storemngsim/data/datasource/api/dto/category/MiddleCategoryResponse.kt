package com.tenutz.storemngsim.data.datasource.api.dto.category

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class MiddleCategoryResponse(
    val storeCode: String,
    val mainCategoryCode: String,
    val categoryCode: String,
    val categoryName: String?,
    val use: Boolean,
    val imageName: String?,
    val imageUrl: String?,
    val order: Int?,
    val businessNumber: String?,
    val representativeName: String?,
    val tel: String?,
    val address: String?,
    val tid: String?,
    val creator: String?,
    val createdAt: Date?,
    val lastModifier: String?,
    val lastModifiedAt: Date?,
): Parcelable {

    companion object {
        fun empty() = MiddleCategoryResponse(
            storeCode = "",
            mainCategoryCode = "",
            categoryCode = "",
            categoryName = "",
            use = false,
            imageName = "",
            imageUrl = "",
            order = 0,
            businessNumber = "",
            representativeName = "",
            tel = "",
            address = "",
            tid = "",
            creator = "",
            createdAt = Date(),
            lastModifier = "",
            lastModifiedAt = Date(),
        )
    }
}
