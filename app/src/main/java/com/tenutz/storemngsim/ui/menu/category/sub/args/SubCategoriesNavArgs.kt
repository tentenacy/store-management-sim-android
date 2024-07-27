package com.tenutz.storemngsim.ui.menu.category.sub.args

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class SubCategoriesNavArgs(
    val storeCode: String,
    val mainCategoryCode: String,
    val categoryCode: String,
    val categoryName: String?,
    val imageName: String? = null,
    val imageUrl: String? = null,
    val tel: String?,
    val address: String?,
    val createdAt: Date? = null,
    val lastModifiedAt: Date? = null,
): Parcelable
