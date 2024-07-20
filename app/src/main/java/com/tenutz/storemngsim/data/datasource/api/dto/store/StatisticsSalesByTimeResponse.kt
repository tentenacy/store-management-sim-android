package com.tenutz.storemngsim.data.datasource.api.dto.store

import java.util.*

data class StatisticsSalesByTimeResponse(
    val contents: List<StatisticsSalesByTime>,
) {
    data class StatisticsSalesByTime(
        val dateHour: Date?,
        val salesAmount: Int,
        val salesCount: Int,
        val cashSalesAmount: Int,
        val cashSalesCount: Int,
        val cashSalesCancelAmount: Int,
        val cashSalesCancelCount: Int,
        val creditCardSalesAmount: Int,
        val creditCardSalesCount: Int,
        val creditCardSalesCancelAmount: Int,
        val creditCardSalesCancelCount: Int,
    )
}
