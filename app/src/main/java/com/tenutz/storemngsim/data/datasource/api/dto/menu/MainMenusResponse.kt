package com.tenutz.storemngsim.data.datasource.api.dto.menu

import kotlinx.serialization.Transient

data class MainMenusResponse(
    val mainMenus: List<MainMenu>,
) {
    data class MainMenu(
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
        @Transient var hideRemoval: Boolean = false,
    )
}
