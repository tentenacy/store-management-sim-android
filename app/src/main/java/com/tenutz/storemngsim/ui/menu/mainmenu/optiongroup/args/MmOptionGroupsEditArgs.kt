package com.tenutz.storemngsim.ui.menu.mainmenu.optiongroup.args

data class MmOptionGroupsEditArgs(
    val mmOptionGroupsEdit: List<MainMenuOptionGroup>,
) {
    data class MainMenuOptionGroup(
        val optionGroupCode: String?,
        val optionName: String?,
        val priority: Int?,
        var checked: Boolean = false,
    )
}
