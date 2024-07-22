package com.tenutz.storemngsim.data.datasource.api.dto.store

import com.google.gson.annotations.SerializedName

data class StatisticsSalesByMenusTodayResponse(
    val contents: List<StatisticsSalesByMenuToday>
) {

    data class StatisticsSalesByMenuToday(
        val menuName: String?,
        val amount: Int,
        val count: Int,
        @SerializedName("percent") val ratio: Float,
    )
}
