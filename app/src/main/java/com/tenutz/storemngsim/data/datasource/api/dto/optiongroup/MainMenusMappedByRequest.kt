package com.tenutz.storemngsim.data.datasource.api.dto.optiongroup

data class MainMenusMappedByRequest(
    val mainMenusMappedBy: List<MainMenuMappedBy>,
) {
    data class MainMenuMappedBy(
        val mainCategoryCode: String,
        val middleCategoryCode: String,
        val subCategoryCode: String,
        val menuCode: String,
    )
}
