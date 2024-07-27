package com.tenutz.storemngsim.ui.menu.mainmenu.optiongroup.args

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MmOptionGroupsNavArgs(
    val menuCode: String,
    val menuName: String?,
    val imageUrl: String?,
    val outOfStock: Boolean,
    val price: Int,
    val discountingPrice: Int,
    val discountedPrice: Int,
    val use: Boolean?,
): Parcelable