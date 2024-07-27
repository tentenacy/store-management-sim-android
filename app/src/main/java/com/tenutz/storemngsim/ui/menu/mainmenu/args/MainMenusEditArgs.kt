package com.tenutz.storemngsim.ui.menu.mainmenu.args

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MainMenusEditArgs(
    val mainMenusEdit: List<MainMenuEdit>
): Parcelable {
    @Parcelize
    data class MainMenuEdit(
        val storeCode: String?,
        val mainCategoryCode: String?,
        val middleCategoryCode: String?,
        val subCategoryCode: String?,
        val menuCode: String?,
        val menuName: String?,
        val imageUrl: String?,
        val outOfStock: Boolean,
        val price: Int,
        val discountingPrice: Int,
        val discountedPrice: Int,
        val use: Boolean?,
        val subCategoryName: String?,
        var checked: Boolean = false,
    ): Parcelable
}