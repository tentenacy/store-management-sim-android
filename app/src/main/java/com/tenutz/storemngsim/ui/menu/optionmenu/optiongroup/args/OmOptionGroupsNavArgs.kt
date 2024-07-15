package com.tenutz.storemngsim.ui.menu.optionmenu.optiongroup.args

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OmOptionGroupsNavArgs(
    val storeCode: String?,
    val optionCode: String,
    val optionName: String?,
    val imageUrl: String?,
    val outOfStock: Boolean,
    val price: Int,
    val use: Boolean?,
): Parcelable