package com.tenutz.storemngsim.data.datasource.api.dto.menu

data class MainMenuOptionGroupsResponse(
    val mainMenuOptionGroups: List<MainMenuOptionGroup>,
) {
    data class MainMenuOptionGroup(
        val optionGroupCode: String?,
        val optionName: String?,
        val toggleSelect: Boolean,
        val required: Boolean,
    )
}
