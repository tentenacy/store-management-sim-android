package com.tenutz.storemngsim.data.datasource.api.dto.optiongroup

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OptionGroupOptionMappersResponse(
    val optionGroupOptionMappers: List<OptionGroupOptionMapper>,
): Parcelable {
    @Parcelize
    data class OptionGroupOptionMapper(
        val storeCode: String?,
        val optionCode: String?,
        val optionName: String?,
        val imageUrl: String?,
        val outOfStock: Boolean,
        val price: Int?,
        val discountingPrice: Int?,
        val discountedPrice: Int?,
        val use: Boolean?,
        val priority: Int?,
    ): Parcelable
}
