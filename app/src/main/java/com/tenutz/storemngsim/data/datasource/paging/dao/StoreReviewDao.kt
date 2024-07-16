package com.tenutz.storemngsim.data.datasource.paging.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tenutz.storemngsim.data.datasource.paging.entity.StoreReviews

@Dao
interface StoreReviewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(messages: List<StoreReviews.StoreReview>)

    @Query("SELECT * FROM store_review ORDER BY seq ASC")
    fun selectAll(): PagingSource<Int, StoreReviews.StoreReview>

    @Query("DELETE FROM store_review")
    fun clear()
}