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
        val price: Int?,
    ): Parcelable
}
