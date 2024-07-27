package com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.args

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MappingMenusNavArgs(
    val optionGroupCode: String,
    val optionGroupName: String?,
    val toggleSelect: Boolean,
    val required: Boolean,
): Parcelable
