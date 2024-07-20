package com.tenutz.storemngsim.data.datasource.paging.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tenutz.storemngsim.data.datasource.paging.entity.StoreReviews

@Dao
interface StoreReviewRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(messages: List<StoreReviews.StoreReviewRemoteKeys>)

    @Query("SELECT * FROM store_review_remote_keys ORDER BY id ASC")
    fun selectAll(): PagingSource<Int, StoreReviews.StoreReviewRemoteKeys>

    @Query("DELETE FROM store_review_remote_keys")
    fun clear()
}