package com.tenutz.storemngsim.ui.menu.optionmenu.optiongroup.args

data class OmOptionGroupsEditArgs(
    val omOptionGroupsEdit: List<OptionOptionGroupEdit>,
) {
    data class OptionOptionGroupEdit(
        val optionGroupCode: String?,
        val optionName: String?,
        val priority: Int?,
        var checked: Boolean = false,
    )
}
