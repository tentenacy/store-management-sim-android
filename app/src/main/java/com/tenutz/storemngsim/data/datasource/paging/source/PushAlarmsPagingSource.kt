package com.tenutz.storemngsim.data.datasource.paging.source

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.tenutz.storemngsim.data.datasource.api.SMSApi
import com.tenutz.storemngsim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.storemngsim.data.datasource.paging.entity.PushAlarms
import com.tenutz.storemngsim.data.datasource.paging.source.mapper.PushAlarmsMapper
import com.tenutz.storemngsim.utils.constant.RetryPolicyConstant
import com.tenutz.storemngsim.utils.ext.applyRetryPolicy
import com.tenutz.storemngsim.utils.ext.toDateTimeFormat
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class PushAlarmsPagingSource(
    private val SMSApi: SMSApi,
    private val mapper: PushAlarmsMapper,
    private val commonCond: CommonCondition,
): RxPagingSource<Int, PushAlarms.PushAlarm>() {

    override fun getRefreshKey(state: PagingState<Int, PushAlarms.PushAlarm>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, PushAlarms.PushAlarm>> {
        val position = params.key ?: 0
        return SMSApi.pushAlarms(
            page = position,
            size = params.loadSize,
            dateFrom = commonCond.dateFrom.toDateTimeFormat(),
            dateTo = commonCond.dateTo.toDateTimeFormat(),
            query = commonCond.query,
            queryType = commonCond.queryType,
        )
            .subscribeOn(Schedulers.io())
            .map { response ->
                mapper.transform(response)
            }
            .map { pushAlarms ->
                toLoadResult(pushAlarms, position)
            }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { LoadResult.Error(it) })
    }

    private fun toLoadResult(data: PushAlarms, position: Int): LoadResult<Int, PushAlarms.PushAlarm> {
        return LoadResult.Page(
            data = data.pushAlarms,
            prevKey = if(position == 0) null else position - 1,
            nextKey = if(data.endOfPage) null else position + 1,
        )
    }
}