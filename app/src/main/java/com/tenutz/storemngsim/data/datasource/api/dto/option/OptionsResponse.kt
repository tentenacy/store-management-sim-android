package com.tenutz.storemngsim.data.datasource.api.dto.option

data class OptionsResponse(
    val options: List<Option>,
) {
    data class Option(
        val storeCode: String?,
        val optionCode: String?,
        val optionName: String?,
        val imageUrl: String?,
        val outOfStock: Boolean,
        val price: Int,
        val use: Boolean?,
    )
}
