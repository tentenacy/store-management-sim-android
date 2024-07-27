package com.tenutz.storemngsim.data.datasource.paging.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tenutz.storemngsim.data.datasource.paging.entity.MenuReviews

@Dao
interface MenuReviewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(messages: List<MenuReviews.MenuReview>)

    @Query("SELECT * FROM menu_review ORDER BY seq ASC")
    fun selectAll(): PagingSource<Int, MenuReviews.MenuReview>

    @Query("DELETE FROM menu_review")
    fun clear()
}