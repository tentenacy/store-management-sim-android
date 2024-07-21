package com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.mainmenu.args

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OgOptionMenuAddArgs(
    val ogOptionMenus: List<OptionGroupOptionMenus>,
): Parcelable {
    @Parcelize
    data class OptionGroupOptionMenus(
        val storeCode: String?,
        val optionCode: String?,
        val optionName: String?,
        val imageUrl: String?,
        val outOfStock: Boolean,
        val price: Int?,
        val discountingPrice: Int,
        val discountedPrice: Int,
        val use: Boolean?,
    ): Parcelable
}
