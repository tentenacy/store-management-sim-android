package com.tenutz.storemngsim.data.datasource.api.dto.store

data class StatisticsSalesTotalByTimeResponse(
    val salesAmountTotal: Int,
    val salesCountTotal: Int,
    val cashSalesAmountTotal: Int,
    val cashSalesCountTotal: Int,
    val cashSalesCancelAmountTotal: Int,
    val cashSalesCancelCountTotal: Int,
    val creditCardSalesAmountTotal: Int,
    val creditCardSalesCountTotal: Int,
    val creditCardSalesCancelAmountTotal: Int,
    val creditCardSalesCancelCountTotal: Int,
)
