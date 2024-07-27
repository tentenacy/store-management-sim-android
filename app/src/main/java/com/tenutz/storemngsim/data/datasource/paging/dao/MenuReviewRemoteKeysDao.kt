package com.tenutz.storemngsim.data.datasource.paging.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tenutz.storemngsim.data.datasource.paging.entity.MenuReviews

@Dao
interface MenuReviewRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(messages: List<MenuReviews.MenuReviewRemoteKeys>)

    @Query("SELECT * FROM menu_review_remote_keys ORDER BY id ASC")
    fun selectAll(): PagingSource<Int, MenuReviews.MenuReviewRemoteKeys>

    @Query("DELETE FROM menu_review_remote_keys")
    fun clear()
}