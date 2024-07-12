package com.tenutz.storemngsim.data.datasource.api.dto.optiongroup

data class OptionGroupOptionMapperPrioritiesChangeRequest(
    val optionGroupOptions: List<OptionGroupOption>,
) {
    data class OptionGroupOption(
        val optionCode: String,
        val priority: Int,
    )
}
