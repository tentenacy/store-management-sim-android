package com.tenutz.storemngsim.data.datasource.api.dto.optiongroup

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OptionGroupMainMenusResponse(
    val optionGroupMainMenus: List<OptionGroupMainMenu>,
): Parcelable {
    @Parcelize
    data class OptionGroupMainMenu(
        val storeCode: String?,
        val menuCode: String?,
        val menuName: String?,
        val price: Int?,
        val mainCategoryCode: String?,
        val middleCategoryCode: String?,
        val subCategoryCode: String?,
    ): Parcelable
}
