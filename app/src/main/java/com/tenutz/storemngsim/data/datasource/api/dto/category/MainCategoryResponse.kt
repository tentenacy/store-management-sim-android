package com.tenutz.storemngsim.data.datasource.api.dto.category

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class MainCategoryResponse(
    val storeCode: String?,
    val categoryCode: String?,
    val categoryName: String?,
    val use: Boolean,
    val order: Int?,
    val creator: String?,
    val createdAt: Date?,
    val lastModifier: String?,
    val lastModifiedAt: Date?,
): Parcelable
