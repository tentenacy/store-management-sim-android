package com.tenutz.storemngsim.data.datasource.api.dto.category


data class CategoryPrioritiesChangeRequest(
    val categories: List<MainCategory>,
) {
    data class MainCategory(
        val categoryCode: String,
        val priority: Int,
    )
}