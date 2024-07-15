package com.tenutz.storemngsim.data.datasource.api.dto.optiongroup

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OptionGroupOptionMappersResponse(
    val optionGroupOptionMappers: List<OptionGroupOptionMapper>,
): Parcelable {
    @Parcelize
    data class OptionGroupOptionMapper(
        val storeCode: String?,
        val optionCode: String?,
        val optionName: String?,
        val price: Int?,
        val use: Boolean?,
        val priority: Int?,
    ): Parcelable
}
