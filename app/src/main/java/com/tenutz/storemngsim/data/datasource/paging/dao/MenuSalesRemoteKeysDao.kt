package com.tenutz.storemngsim.data.datasource.paging.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tenutz.storemngsim.data.datasource.paging.entity.MenuSalesList

@Dao
interface MenuSalesRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(messages: List<MenuSalesList.MenuSalesRemoteKeys>)

    @Query("SELECT * FROM menu_sales_remote_keys ORDER BY id ASC")
    fun selectAll(): PagingSource<Int, MenuSalesList.MenuSalesRemoteKeys>

    @Query("DELETE FROM menu_sales_remote_keys")
    fun clear()
}