package com.tenutz.storemngsim.data.datasource.paging.source

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.tenutz.storemngsim.data.datasource.api.SMSApi
import com.tenutz.storemngsim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.storemngsim.data.datasource.api.dto.store.SalesRequest
import com.tenutz.storemngsim.data.datasource.paging.entity.SalesList
import com.tenutz.storemngsim.data.datasource.paging.source.mapper.SalesMapper
import com.tenutz.storemngsim.utils.constant.RetryPolicyConstant
import com.tenutz.storemngsim.utils.ext.applyRetryPolicy
import com.tenutz.storemngsim.utils.ext.toDateTimeFormat
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class SalesPagingSource(
    private val SMSApi: SMSApi,
    private val mapper: SalesMapper,
    private val commonCond: CommonCondition,
    private val cond: SalesRequest,
): RxPagingSource<Int, SalesList.Sales>() {

    override fun getRefreshKey(state: PagingState<Int, SalesList.Sales>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, SalesList.Sales>> {
        val position = params.key ?: 0
        return SMSApi.sales(
            page = position,
            size = params.loadSize,
            dateFrom = commonCond.dateFrom.toDateTimeFormat(),
            dateTo = commonCond.dateTo.toDateTimeFormat(),
            query = commonCond.query,
            queryType = commonCond.queryType,
            paymentType = cond.paymentType,
            approvalType = cond.approvalType,
            orderType = cond.orderType,
        )
            .subscribeOn(Schedulers.io())
            .map { response ->
                mapper.transform(response)
            }
            .map { salesList ->
                toLoadResult(salesList, position)
            }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                    RetryPolicyConstant.ACCESS_TOKEN_EXPIRED,
                ) { LoadResult.Error(it) })
    }

    private fun toLoadResult(data: SalesList, position: Int): LoadResult<Int, SalesList.Sales> {
        return LoadResult.Page(
            data = data.salesList,
            prevKey = if(position == 0) null else position - 1,
            nextKey = if(data.endOfPage) null else position + 1,
        )
    }
}