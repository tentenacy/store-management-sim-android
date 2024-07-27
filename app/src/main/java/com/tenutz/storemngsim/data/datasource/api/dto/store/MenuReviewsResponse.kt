package com.tenutz.storemngsim.data.datasource.api.dto.store

import java.util.Date

data class MenuReviewsResponse(
    val seq: Long,
    val siteCode: String?,
    val storeCode: String?,
    val mainCategoryCode: String?,
    val middleCategoryCode: String?,
    val subCategoryCode: String?,
    val menuCode: String?,
    val menuName: String?,
    val imageUrl: String?,
    val createdBy: String?,
    val createdAt: Date?,
    val content: String?,
    val keyword: Int,
    val level: Int,
    val rating: Int,
    val sno: Int,
    val menuReviewReply: MenuReviewReply?,
) {
    data class MenuReviewReply(
        val seq: Long,
        val siteCode: String?,
        val storeCode: String?,
        val mainCategoryCode: String?,
        val middleCategoryCode: String?,
        val subCategoryCode: String?,
        val menuCode: String?,
        val createdBy: String?,
        val createdAt: Date?,
        val content: String?,
    )
}
