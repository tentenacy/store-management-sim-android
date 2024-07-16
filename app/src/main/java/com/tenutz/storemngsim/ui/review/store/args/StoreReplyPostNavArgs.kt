package com.tenutz.storemngsim.ui.review.store.args

import android.os.Parcelable
import com.tenutz.storemngsim.data.datasource.paging.entity.StoreReviews
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class StoreReplyPostNavArgs(
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
    val storeReviewReply: StoreReviews.StoreReview.StoreReviewReply? = null,
): Parcelable
