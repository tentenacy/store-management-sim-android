package com.tenutz.storemngsim.data.datasource.paging.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tenutz.storemngsim.data.datasource.paging.entity.SalesList

@Dao
interface SalesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(messages: List<SalesList.Sales>)

    @Query("SELECT * FROM sales ORDER BY id")
    fun selectAll(): PagingSource<Int, SalesList.Sales>

    @Query("DELETE FROM sales")
    fun clear()
}