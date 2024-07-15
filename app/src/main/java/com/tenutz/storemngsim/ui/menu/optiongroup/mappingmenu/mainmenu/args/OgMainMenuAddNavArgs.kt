package com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.mainmenu.args

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OgMainMenuAddNavArgs(
    val optionGroupCode: String,
    val mainCategoryCode: String,
    val middleCategoryCode: String,
    val subCategoryCode: String,
): Parcelable
