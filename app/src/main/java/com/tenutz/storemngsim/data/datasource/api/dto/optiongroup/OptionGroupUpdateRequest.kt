package com.tenutz.storemngsim.data.datasource.api.dto.optiongroup

data class OptionGroupUpdateRequest(
    val optionGroupName: String,
    val toggleSelect: Boolean,
    val required: Boolean,
)
