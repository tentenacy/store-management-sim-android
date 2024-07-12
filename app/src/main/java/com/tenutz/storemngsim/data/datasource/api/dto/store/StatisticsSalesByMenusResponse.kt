package com.tenutz.storemngsim.data.datasource.api.dto.store

data class StatisticsSalesByMenusResponse(
    val soldAt: String?,
    val menuName: String?,
    val categoryName: String?,
    val mainEquipmentName: String?,
    val middleEquipmentName: String?,
    val mainCategoryCode: String?,
    val middleCategoryCode: String?,
    val subCategoryCode: String?,
    val cAuthAmount: Int,
    val cAuthCount: Int,
    val authAmount: Int,
    val authCount: Int,
    val authCAmount: Int,
    val authCCount: Int,
)
