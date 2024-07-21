package com.tenutz.storemngsim.data.datasource.api.dto.optiongroup

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OptionGroupMainMenuMappersResponse(
    val optionGroupMainMenuMappers: List<OptionGroupMainMenuMapper>,
): Parcelable {
    @Parcelize
    data class OptionGroupMainMenuMapper(
        val storeCode: String?,
        val mainCategoryCode: String?,
        val middleCategoryCode: String?,
        val subCategoryCode: String?,
        val menuCode: String?,
        val menuName: String?,
        val imageUrl: String?,
        val outOfStock: Boolean,
        val price: Int?,
        val discountingPrice: Int,
        val discountedPrice: Int,
        val use: Boolean?,
        val priority: Int?,
    ): Parcelable
}
