package com.tenutz.storemngsim.data.datasource.api.dto.optiongroup

data class OptionGroupMainMenusResponse(
    val optionGroupMainMenus: List<OptionGroupMainMenu>,
) {
    data class OptionGroupMainMenu(
        val storeCode: String?,
        val menuCode: String?,
        val menuName: String?,
        val price: Int?,
        val mainCategoryCode: String?,
        val middleCategoryCode: String?,
        val subCategoryCode: String?,
    )
}
