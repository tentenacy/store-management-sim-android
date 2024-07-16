package com.tenutz.storemngsim.data.datasource.paging.repository

import androidx.paging.PagingData
import com.tenutz.storemngsim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.storemngsim.data.datasource.paging.entity.MenuReviews
import com.tenutz.storemngsim.data.datasource.paging.entity.StoreReviews
import io.reactivex.rxjava3.core.Flowable

interface ReviewPagingRepository {
    fun storeReviews(commonCond: CommonCondition): Flowable<PagingData<StoreReviews.StoreReview>>
    fun menuReviews(commonCond: CommonCondition): Flowable<PagingData<MenuReviews.MenuReview>>
}