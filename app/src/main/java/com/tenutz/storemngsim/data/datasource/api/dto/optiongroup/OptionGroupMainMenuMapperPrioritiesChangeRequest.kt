package com.tenutz.storemngsim.data.datasource.api.dto.optiongroup

data class OptionGroupMainMenuMapperPrioritiesChangeRequest(
    val optionGroupMainMenus: List<OptionGroupMainMenu>,
) {
    data class OptionGroupMainMenu(
        val mainCategoryCode: String,
        val middleCategoryCode: String,
        val subCategoryCode: String,
        val menuCode: String,
        val priority: Int,
    )
}
