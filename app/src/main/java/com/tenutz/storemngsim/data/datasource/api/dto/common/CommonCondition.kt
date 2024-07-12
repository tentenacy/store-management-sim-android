package com.tenutz.storemngsim.data.datasource.api.dto.common

import java.util.*

data class CommonCondition(
    val dateFrom: Date?,
    val dateTo: Date?,
    val query: String?,
    val queryType: String?,
)
