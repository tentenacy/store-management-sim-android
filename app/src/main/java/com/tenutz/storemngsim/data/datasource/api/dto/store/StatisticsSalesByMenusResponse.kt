package com.tenutz.storemngsim.data.datasource.api.dto.store

import com.google.gson.annotations.SerializedName
import java.util.*

data class StatisticsSalesByMenusResponse(
    val soldAt: Date?,
    val menuName: String?,
    val categoryName: String?,
    val mainEquipmentName: String?,
    val middleEquipmentName: String?,
    val mainCategoryCode: String?,
    val middleCategoryCode: String?,
    val subCategoryCode: String?,
    val menuCode: String?,
    @SerializedName("cauthAmount") val cAuthAmount: Int,
    @SerializedName("cauthCount") val cAuthCount: Int,
    val authAmount: Int,
    val authCount: Int,
    val authCAmount: Int,
    val authCCount: Int,
)