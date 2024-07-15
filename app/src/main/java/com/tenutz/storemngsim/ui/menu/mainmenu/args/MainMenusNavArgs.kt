package com.tenutz.storemngsim.ui.menu.mainmenu.args

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MainMenusNavArgs(
    val mainCategoryCode: String,
    val middleCategoryCode: String,
    val subCategoryCode: String,
): Parcelable