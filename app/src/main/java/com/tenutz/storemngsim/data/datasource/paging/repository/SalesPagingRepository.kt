package com.tenutz.storemngsim.data.datasource.paging.repository

import androidx.paging.PagingData
import com.tenutz.storemngsim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.storemngsim.data.datasource.paging.entity.MenuSalesList
import io.reactivex.rxjava3.core.Flowable

interface SalesPagingRepository {

    fun menuSalesList(commonCond: CommonCondition): Flowable<PagingData<MenuSalesList.MenuSales>>
}