package com.tenutz.storemngsim.data.datasource.api.dto.menu

data class MainMenuMappersResponse(
    val mainMenuMappers: List<MainMenuMapper>,
) {
    data class MainMenuMapper(
        val optionGroupCode: String?,
        val optionName: String?,
        val priority: Int?,
    )
}