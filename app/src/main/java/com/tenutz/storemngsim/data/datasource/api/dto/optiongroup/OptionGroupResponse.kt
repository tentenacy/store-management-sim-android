package com.tenutz.storemngsim.data.datasource.api.dto.optiongroup

import java.util.Date

data class OptionGroupResponse(
    val storeCode: String?,
    val optionGroupCode: String?,
    val optionGroupName: String?,
    val toggleSelect: Boolean,
    val required: Boolean,
    val use: Boolean,
    val priority: Int?,
    val creator: String?,
    val createdAt: Date?,
    val lastModifier: String?,
    val lastModifiedAt: Date?,
)
