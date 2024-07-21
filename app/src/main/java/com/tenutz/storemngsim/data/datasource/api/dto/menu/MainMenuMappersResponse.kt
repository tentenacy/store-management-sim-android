package com.tenutz.storemngsim.data.datasource.api.dto.menu

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MainMenuMappersResponse(
    val mainMenuMappers: List<MainMenuMapper>,
): Parcelable {

    @Parcelize
    data class MainMenuMapper(
        val optionGroupCode: String?,
        val optionName: String?,
        val toggleSelect: Boolean,
        val required: Boolean,
        val priority: Int?,
    ): Parcelable
}