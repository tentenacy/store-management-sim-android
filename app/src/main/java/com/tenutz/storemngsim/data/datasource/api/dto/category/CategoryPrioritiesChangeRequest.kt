package com.tenutz.storemngsim.data.datasource.api.dto.category

import org.jetbrains.annotations.NotNull




data class CategoryPrioritiesChangeRequest(
    val categories: List<MainCategory>,
) {
    data class MainCategory(
        val categoryCode: String,
        val priority: Int,
    )
}