package com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.mainmenu.args

import android.os.Parcelable
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.OptionGroupMainMenuMappersResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class OgMainMenuAddArgs(
    val ogMainMenus: List<OptionGroupMainMenus>,
): Parcelable {
    @Parcelize
    data class OptionGroupMainMenus(
        val storeCode: String?,
        val menuCode: String?,
        val menuName: String?,
        val imageUrl: String?,
        val outOfStock: Boolean,
        val price: Int?,
        val discountingPrice: Int,
        val discountedPrice: Int,
        val use: Boolean?,
        val mainCategoryCode: String?,
        val middleCategoryCode: String?,
        val subCategoryCode: String?,
        val mainCategoryName: String?,
        val middleCategoryName: String?,
        val subCategoryName: String?,
        var expanded: Boolean = false,
    ): Parcelable
}
