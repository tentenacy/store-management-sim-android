package com.tenutz.storemngsim.data.datasource.api.dto.option

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OptionsResponse(
    val options: List<Option>,
): Parcelable {
    @Parcelize
    data class Option(
        val storeCode: String,
        val optionCode: String,
        val optionName: String?,
        val imageUrl: String?,
        val price: Int,
        val use: Boolean?,
    ): Parcelable
}
