package com.tenutz.storemngsim.data.datasource.api.dto.optiongroup

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OptionGroupsResponse(
    val optionGroups: List<OptionGroup>,
): Parcelable {
    @Parcelize
    data class OptionGroup(
        val optionGroupCode: String,
        val optionGroupName: String?,
        val toggleSelect: Boolean,
        val required: Boolean,
    ): Parcelable
}
