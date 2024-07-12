package com.tenutz.storemngsim.data.datasource.api.dto.option

data class OptionMappersResponse(
    val optionMappers: List<OptionMapper>,
) {
    data class OptionMapper(
        val optionGroupCode: String?,
        val optionName: String?,
        val priority: Int?,
    )
}
