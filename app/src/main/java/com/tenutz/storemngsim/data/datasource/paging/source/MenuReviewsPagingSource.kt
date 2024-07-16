package com.tenutz.storemngsim.data.datasource.paging.source

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.tenutz.storemngsim.data.datasource.api.SMSApi
import com.tenutz.storemngsim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.storemngsim.data.datasource.paging.entity.MenuReviews
import com.tenutz.storemngsim.data.datasource.paging.source.mapper.ReviewsMapper
import com.tenutz.storemngsim.utils.constant.RetryPolicyConstant
import com.tenutz.storemngsim.utils.ext.applyRetryPolicy
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MenuReviewsPagingSource @Inject constructor(
    private val sckApi: SMSApi,
    private val mapper: ReviewsMapper,
    private val commonCond: CommonCondition,
): RxPagingSource<Int, MenuReviews.MenuReview>() {

    override fun getRefreshKey(state: PagingState<Int, MenuReviews.MenuReview>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, MenuReviews.MenuReview>> {
        val position = params.key ?: 0
        return sckApi.menuReviews(
            page = position,
            size = params.loadSize,
            dateFrom = commonCond.dateFrom,
            dateTo = commonCond.dateTo,
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
                ) { LoadResult.Error(it) })
    }

    private fun toLoadResult(data: MenuReviews, position: Int): LoadResult<Int, MenuReviews.MenuReview> {
        return LoadResult.Page(
            data = data.menuReviews,
            prevKey = if(position == 0) null else position - 1,
            nextKey = if(data.endOfPage) null else position + 1,
        )
    }
}