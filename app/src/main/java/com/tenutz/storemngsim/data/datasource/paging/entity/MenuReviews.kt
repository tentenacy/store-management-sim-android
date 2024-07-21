package com.tenutz.storemngsim.data.datasource.paging.entity

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class MenuReviews(
    val total: Int = 0,
    val page: Int = 0,
    val menuReviews: List<MenuReview>
): Parcelable {

    @IgnoredOnParcel
    val endOfPage = total - 1 <= page

    @Parcelize
    @Entity(tableName = "menu_review")
    data class MenuReview(
        @PrimaryKey val seq: Long,
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
        @Embedded(prefix = "reply_") val menuReviewReply: MenuReviewReply?,
    ): Parcelable {

        @Parcelize
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
        ): Parcelable
    }

    @Parcelize
    @Entity(tableName = "menu_review_remote_keys")
    data class MenuReviewRemoteKeys(
        @PrimaryKey(autoGenerate = true) val id: Long,
        val prevKey: Int?,
        val nextKey: Int,
    ): Parcelable
}