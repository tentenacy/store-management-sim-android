package com.tenutz.storemngsim.data.datasource.api.dto.optiongroup

data class OptionGroupOptionsResponse(
    val optionGroupOptions: List<OptionGroupOption>,
) {
    data class OptionGroupOption(
        val storeCode: String?,
        val optionCode: String?,
        val optionName: String?,
        val price: Int?,
    )
}
