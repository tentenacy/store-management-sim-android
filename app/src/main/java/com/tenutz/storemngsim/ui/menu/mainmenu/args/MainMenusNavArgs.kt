package com.tenutz.storemngsim.ui.menu.mainmenu.args

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MainMenusNavArgs(
    val subCategoryCode: String,
    val subCategoryName: String,
): Parcelable