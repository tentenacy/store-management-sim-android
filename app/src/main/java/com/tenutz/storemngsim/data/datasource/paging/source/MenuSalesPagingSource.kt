package com.tenutz.storemngsim.data.datasource.paging.source

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.tenutz.storemngsim.data.datasource.api.SMSApi
import com.tenutz.storemngsim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.storemngsim.data.datasource.paging.entity.MenuSalesList
import com.tenutz.storemngsim.data.datasource.paging.source.mapper.SalesMapper
import com.tenutz.storemngsim.utils.constant.RetryPolicyConstant
import com.tenutz.storemngsim.utils.ext.applyRetryPolicy
import com.tenutz.storemngsim.utils.ext.toDateTimeFormat
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class MenuSalesPagingSource(
    private val SMSApi: SMSApi,
    private val mapper: SalesMapper,
    private val commonCond: CommonCondition,
): RxPagingSource<Int, MenuSalesList.MenuSales>() {

    override fun getRefreshKey(state: PagingState<Int, MenuSalesList.MenuSales>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, MenuSalesList.MenuSales>> {
        val position = params.key ?: 0
        return SMSApi.statisticsSalesByMenu(
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
            .map { menuReviews ->
                toLoadResult(menuReviews, position)
            }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                    RetryPolicyConstant.ACCESS_TOKEN_EXPIRED,
                ) { LoadResult.Error(it) })
    }

    private fun toLoadResult(data: MenuSalesList, position: Int): LoadResult<Int, MenuSalesList.MenuSales> {
        return LoadResult.Page(
            data = data.menuSalesList,
            prevKey = if(position == 0) null else position - 1,
            nextKey = if(data.endOfPage) null else position + 1,
        )
    }
}