package com.tenutz.storemngsim.data.datasource.paging.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tenutz.storemngsim.data.datasource.paging.entity.SalesList

@Dao
interface SalesRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(messages: List<SalesList.SalesRemoteKeys>)

    @Query("SELECT * FROM sales_remote_keys ORDER BY id")
    fun selectAll(): PagingSource<Int, SalesList.SalesRemoteKeys>

    @Query("DELETE FROM sales_remote_keys")
    fun clear()
}