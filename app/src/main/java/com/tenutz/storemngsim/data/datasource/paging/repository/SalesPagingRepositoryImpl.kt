package com.tenutz.storemngsim.data.datasource.paging.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.tenutz.storemngsim.data.datasource.api.SMSApi
import com.tenutz.storemngsim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.storemngsim.data.datasource.api.dto.store.SalesRequest
import com.tenutz.storemngsim.data.datasource.paging.entity.MenuSalesList
import com.tenutz.storemngsim.data.datasource.paging.entity.SalesList
import com.tenutz.storemngsim.data.datasource.paging.source.MenuSalesPagingSource
import com.tenutz.storemngsim.data.datasource.paging.source.SalesPagingSource
import com.tenutz.storemngsim.data.datasource.paging.source.mapper.SalesMapper
import io.reactivex.rxjava3.core.Flowable

class SalesPagingRepositoryImpl(
    private val SMSApi: SMSApi,
    private val mapper: SalesMapper,
) : SalesPagingRepository {

    override fun salesList(
        commonCond: CommonCondition,
        request: SalesRequest
    ): Flowable<PagingData<SalesList.Sales>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                prefetchDistance = 5,
                initialLoadSize = 20,
            ),
            pagingSourceFactory = { SalesPagingSource(SMSApi, mapper, commonCond, request) }
        ).flowable
    }

    override fun menuSalesList(commonCond: CommonCondition): Flowable<PagingData<MenuSalesList.MenuSales>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                prefetchDistance = 5,
                initialLoadSize = 20,
            ),
            pagingSourceFactory = { MenuSalesPagingSource(SMSApi, mapper, commonCond) }
        ).flowable
    }
}