package com.tenutz.storemngsim.data.datasource.api.dto.optiongroup

data class OptionGroupOptionMappersResponse(
    val optionGroupOptionMappers: List<OptionGroupOptionMapper>,
) {
    data class OptionGroupOptionMapper(
        val storeCode: String?,
        val optionCode: String?,
        val optionName: String?,
        val price: Int?,
        val use: Boolean?,
        val priority: Int?,
    )
}
