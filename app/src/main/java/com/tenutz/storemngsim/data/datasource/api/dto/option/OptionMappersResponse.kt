package com.tenutz.storemngsim.data.datasource.api.dto.option

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OptionMappersResponse(
    val optionMappers: List<OptionMapper>,
): Parcelable {
    @Parcelize
    data class OptionMapper(
        val optionGroupCode: String?,
        val optionName: String?,
        val priority: Int?,
    ): Parcelable
}
