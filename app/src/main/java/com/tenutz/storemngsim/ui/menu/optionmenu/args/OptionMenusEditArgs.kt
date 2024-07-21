package com.tenutz.storemngsim.ui.menu.optionmenu.args

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OptionMenusEditArgs(
    val optionMenusEdit: List<OptionEdit>,
): Parcelable {
    @Parcelize
    data class OptionEdit(
        val storeCode: String?,
        val optionCode: String?,
        val optionName: String?,
        val imageUrl: String?,
        val outOfStock: Boolean,
        val price: Int,
        val discountingPrice: Int,
        val discountedPrice: Int,
        val use: Boolean?,
        var checked: Boolean = false,
    ): Parcelable
}