package com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.mainmenu.args

import android.os.Parcelable
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.OptionGroupMainMenuMappersResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class OgOptionMenusEditArgs(
    val ogOptionMenuMappersEdit: List<OptionGroupOptionMenuMapper>,
): Parcelable {
    @Parcelize
    data class OptionGroupOptionMenuMapper(
        val storeCode: String?,
        val optionCode: String?,
        val optionName: String?,
        val imageUrl: String?,
        val outOfStock: Boolean,
        val price: Int?,
        val discountingPrice: Int?,
        val discountedPrice: Int?,
        val use: Boolean?,
        val priority: Int?,
        var checked: Boolean = false,
    ): Parcelable
}
