package com.tenutz.storemngsim.data.datasource.paging.repository

import androidx.paging.PagingData
import com.tenutz.storemngsim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.storemngsim.data.datasource.api.dto.store.SalesRequest
import com.tenutz.storemngsim.data.datasource.paging.entity.MenuSalesList
import com.tenutz.storemngsim.data.datasource.paging.entity.PushAlarms
import com.tenutz.storemngsim.data.datasource.paging.entity.SalesList
import io.reactivex.rxjava3.core.Flowable

interface PushAlarmPagingRepository {

    fun pushAlarms(commonCond: CommonCondition): Flowable<PagingData<PushAlarms.PushAlarm>>
}