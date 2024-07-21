package com.tenutz.storemngsim.data.datasource.api.dto.optiongroup

data class OptionGroupOptionsResponse(
    val optionGroupOptions: List<OptionGroupOption>,
) {
    data class OptionGroupOption(
        val storeCode: String?,
        val optionCode: String?,
        val optionName: String?,
        val imageUrl: String?,
        val outOfStock: Boolean,
        val price: Int?,
        val discountingPrice: Int,
        val discountedPrice: Int,
        val use: Boolean?,
    )
}
