package com.tenutz.storemngsim.ui.menu.optiongroup.args

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OptionGroupsEditArgs(
    val optionGroupsEdit: List<OptionGroup>,
): Parcelable {
    @Parcelize
    data class OptionGroup(
        val optionGroupCode: String?,
        val optionGroupName: String?,
        val toggleSelect: Boolean,
        val required: Boolean,
        var checked: Boolean = false,
    ): Parcelable
}
