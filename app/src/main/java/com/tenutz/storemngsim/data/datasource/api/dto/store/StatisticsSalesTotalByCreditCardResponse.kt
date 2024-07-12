package com.tenutz.storemngsim.data.datasource.api.dto.store

data class StatisticsSalesTotalByCreditCardResponse(
    val salesAmountTotal: Int,
    val salesCountTotal: Int,
    val creditCardSalesAmountTotal: Int,
    val creditCardSalesCountTotal: Int,
    val creditCardSalesCancelAmountTotal: Int,
    val creditCardSalesCancelCountTotal: Int,
)
