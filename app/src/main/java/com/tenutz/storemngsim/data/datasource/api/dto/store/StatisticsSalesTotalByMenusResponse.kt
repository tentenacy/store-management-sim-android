package com.tenutz.storemngsim.data.datasource.api.dto.store

import com.google.gson.annotations.SerializedName

data class StatisticsSalesTotalByMenusResponse(
    @SerializedName("cauthAmountTotal")
    val cAuthAmountTotal: Int,
    @SerializedName("cauthCountTotal")
    val cAuthCountTotal: Int,
    val authAmountTotal: Int,
    val authCountTotal: Int,
    val authCAmountTotal: Int,
    val authCCountTotal: Int,
)
