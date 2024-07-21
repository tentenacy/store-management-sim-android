package com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.mainmenu.args

import android.os.Parcelable
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.OptionGroupMainMenuMappersResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class OgMainMenusEditArgs(
    val ogMainMenuMappersEdit: List<OptionGroupMainMenuMapper>,
): Parcelable {
    @Parcelize
    data class OptionGroupMainMenuMapper(
        val storeCode: String?,
        val mainCategoryCode: String?,
        val middleCategoryCode: String?,
        val subCategoryCode: String?,
        val menuCode: String?,
        val menuName: String?,
        val imageUrl: String?,
        val outOfStock: Boolean,
        val price: Int?,
        val discountingPrice: Int,
        val discountedPrice: Int,
        val use: Boolean?,
        val priority: Int?,
        var checked: Boolean = false,
    ): Parcelable
}
