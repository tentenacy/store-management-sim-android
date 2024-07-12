package com.tenutz.storemngsim.data.datasource.api.dto.common

import java.util.*

data class CommonCondition(
    val dateFrom: Date? = null,
    val dateTo: Date? = null,
    val query: String? = null,
    val queryType: String? = null,
)
