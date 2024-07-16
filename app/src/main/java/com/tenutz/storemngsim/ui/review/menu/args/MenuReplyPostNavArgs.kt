package com.tenutz.storemngsim.ui.review.menu.args

import android.os.Parcelable
import com.tenutz.storemngsim.data.datasource.paging.entity.MenuReviews
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class MenuReplyPostNavArgs(
    val seq: Long,
    val siteCode: String?,
    val storeCode: String?,
    val mainCategoryCode: String?,
    val middleCategoryCode: String?,
    val subCategoryCode: String?,
    val menuCode: String?,
    val menuName: String?,
    val createdBy: String?,
    val createdAt: Date?,
    val content: String?,
    val keyword: Int,
    val level: Int,
    val rating: Int,
    val sno: Int,
    val menuReviewReply: MenuReviews.MenuReview.MenuReviewReply? = null,
): Parcelable
