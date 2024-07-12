package com.tenutz.storemngsim.data.datasource.api.dto.optiongroup

data class OptionGroupsResponse(
    val optionGroups: List<OptionGroup>,
) {
    data class OptionGroup(
        val optionGroupCode: String?,
        val optionGroupName: String?,
        val toggleSelect: Boolean,
        val required: Boolean,
    )
}
