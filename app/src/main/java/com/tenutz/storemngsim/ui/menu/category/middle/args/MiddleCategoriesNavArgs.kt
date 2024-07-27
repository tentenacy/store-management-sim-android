package com.tenutz.storemngsim.ui.menu.category.middle.args

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class MiddleCategoriesNavArgs(
    val storeCode: String?,
    val categoryCode: String,
    val categoryName: String?,
    val use: Boolean,
    val order: Int?,
    val createdAt: Date?,
    val lastModifiedAt: Date?,
): Parcelable