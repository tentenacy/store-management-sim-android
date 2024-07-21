package com.tenutz.storemngsim.data.datasource.paging.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tenutz.storemngsim.data.datasource.paging.entity.MenuSalesList

@Dao
interface MenuSalesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(messages: List<MenuSalesList.MenuSales>)

    @Query("SELECT * FROM menu_sales ORDER BY id")
    fun selectAll(): PagingSource<Int, MenuSalesList.MenuSales>

    @Query("DELETE FROM menu_sales")
    fun clear()
}