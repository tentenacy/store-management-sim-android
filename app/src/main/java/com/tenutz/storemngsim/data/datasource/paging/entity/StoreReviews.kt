package com.tenutz.storemngsim.data.datasource.paging.entity

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class StoreReviews(
    val total: Int = 0,
    val page: Int = 0,
    val storeReviews: List<StoreReview>
): Parcelable {

    @IgnoredOnParcel
    val endOfPage = total - 1 <= page

    @Parcelize
    @Entity(tableName = "store_review")
    data class StoreReview(
        @PrimaryKey val seq: Long,
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
        @Embedded(prefix = "reply_") val storeReviewReply: StoreReviewReply?,
    ): Parcelable {

        @Parcelize
        data class StoreReviewReply(
            val seq: Long,
            val siteCode: String?,
            val storeCode: String?,
            val middleCategoryCode: String?,
            val createdBy: String?,
            val createdAt: Date?,
            val content: String?,
        ): Parcelable
    }

    @Parcelize
    @Entity(tableName = "store_review_remote_keys")
    data class StoreReviewRemoteKeys(
        @PrimaryKey(autoGenerate = true) val id: Long,
        val prevKey: Int?,
        val nextKey: Int,
    ): Parcelable
}