package com.tenutz.storemngsim.data.datasource.api.dto.store

import java.util.*

data class StoreReviewsResponse(
    val seq: Long,
    val siteCode: String?,
    val storeCode: String?,
    val middleCategoryCode: String?,
    val middleCategoryName: String?,
    val createdBy: String?,
    val createdAt: Date?,
    val content: String?,
    val keyword: Int,
    val level: Int,
    val rating: Int,
    val sno: Long,
    val storeReviewReply: StoreReviewReply?,
) {
    data class StoreReviewReply(
        val seq: Long,
        val siteCode: String?,
        val storeCode: String?,
        val middleCategoryCode: String?,
        val createdBy: String?,
        val createdAt: Date?,
        val content: String?,
    )
}
