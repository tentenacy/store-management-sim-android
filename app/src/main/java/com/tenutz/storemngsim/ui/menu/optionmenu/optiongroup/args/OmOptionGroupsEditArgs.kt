package com.tenutz.storemngsim.ui.menu.optionmenu.optiongroup.args

data class OmOptionGroupsEditArgs(
    val optionMappers: List<OptionMapper>,
) {
    data class OptionMapper(
        val optionGroupCode: String?,
        val optionName: String?,
        val priority: Int?,
        var checked: Boolean = false,
    )
}
