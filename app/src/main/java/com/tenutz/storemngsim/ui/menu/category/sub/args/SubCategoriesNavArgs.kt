package com.tenutz.storemngsim.ui.menu.category.sub.args

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class SubCategoriesNavArgs(
    val storeCode: String?,
    val mainCategoryCode: String,
    val categoryCode: String,
    val categoryName: String?,
    val use: Boolean,
    val imageName: String?,
    val imageUrl: String?,
    val order: Int?,
    val createdAt: Date?,
    val lastModifiedAt: Date?,
): Parcelable
