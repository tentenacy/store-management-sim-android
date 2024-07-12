package com.tenutz.storemngsim.data.datasource.api.dto.optiongroup

data class OptionGroupMainMenuMappersResponse(
    val optionGroupMainMenuMappers: List<OptionGroupMainMenuMapper>,
) {
    data class OptionGroupMainMenuMapper(
        val storeCode: String?,
        val mainCategoryCode: String?,
        val middleCategoryCode: String?,
        val subCategoryCode: String?,
        val menuCode: String?,
        val menuName: String?,
        val price: Int?,
        val use: Boolean?,
        val priority: Int?,
    )
}
