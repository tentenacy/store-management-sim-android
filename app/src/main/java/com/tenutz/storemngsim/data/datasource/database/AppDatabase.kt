package com.tenutz.storemngsim.data.datasource.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tenutz.storemngsim.data.datasource.paging.dao.*
import com.tenutz.storemngsim.data.datasource.paging.entity.MenuReviews
import com.tenutz.storemngsim.data.datasource.paging.entity.MenuSalesList
import com.tenutz.storemngsim.data.datasource.paging.entity.StoreReviews
import com.tenutz.storemngsim.utils.converter.RoomConverters

@Database(
    entities = [
        StoreReviews.StoreReview::class,
        StoreReviews.StoreReviewRemoteKeys::class,
        MenuReviews.MenuReview::class,
        MenuReviews.MenuReviewRemoteKeys::class,
        MenuSalesList.MenuSales::class,
        MenuSalesList.MenuSalesRemoteKeys::class,
    ],
    version = 1,
    exportSchema = false,
)
@TypeConverters(RoomConverters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun storeReviewDao(): StoreReviewDao
    abstract fun storeReviewRemoteKeysDao(): StoreReviewRemoteKeysDao
    abstract fun menuReviewDao(): MenuReviewDao
    abstract fun menuReviewRemoteKeysDao(): MenuReviewRemoteKeysDao
    abstract fun menuSalesDao(): MenuSalesDao
    abstract fun menuSalesRemoteKeysDao(): MenuSalesRemoteKeysDao

    companion object {
        fun getInstance(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, "app_db")
                .fallbackToDestructiveMigration()
                .build()
    }
}