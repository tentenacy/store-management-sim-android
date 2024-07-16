package com.tenutz.storemngsim.data.repository.store

import com.tenutz.storemngsim.data.datasource.api.SMSApi
import com.tenutz.storemngsim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.storemngsim.data.datasource.api.dto.store.*
import com.tenutz.storemngsim.utils.constant.RetryPolicyConstant
import com.tenutz.storemngsim.utils.ext.applyRetryPolicy
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class StoreRepositoryImpl @Inject constructor(
    private val sckApi: SMSApi,
) : StoreRepository {
    override fun equipments(): Single<Result<EquipmentsResponse>> =
        sckApi.equipments()
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun salesTotal(
        commonCond: CommonCondition,
        request: SalesRequest
    ): Single<Result<SalesTotalResponse>> =
        sckApi.salesTotal(
            dateFrom = commonCond.dateFrom,
            dateTo = commonCond.dateTo,
            query = commonCond.query,
            queryType = commonCond.queryType,
            paymentType = request.paymentType,
            approvalType = request.approvalType,
            orderType = request.orderType,
        )
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun statisticsSalesTotalByMenu(
        commonCond: CommonCondition,
        request: StatisticsSaleByMenusRequest
    ): Single<Result<StatisticsSalesTotalByMenusResponse>> =
        sckApi.statisticsSalesTotalByMenu(
            dateFrom = commonCond.dateFrom,
            dateTo = commonCond.dateTo,
            query = commonCond.query,
            queryType = commonCond.queryType,
            mainCategoryCode = request.mainCategoryCode,
        )
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun statisticsSalesByCreditCard(commonCond: CommonCondition): Single<Result<StatisticsSalesByCreditCardResponse>> =
        sckApi.statisticsSalesByCreditCard(
            dateFrom = commonCond.dateFrom,
            dateTo = commonCond.dateTo,
            query = commonCond.query,
            queryType = commonCond.queryType,
        )
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun statisticsSalesTotalByCreditCard(commonCond: CommonCondition): Single<Result<StatisticsSalesTotalByCreditCardResponse>> =
        sckApi.statisticsSalesTotalByCreditCard(
            dateFrom = commonCond.dateFrom,
            dateTo = commonCond.dateTo,
            query = commonCond.query,
            queryType = commonCond.queryType,
        )
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun statisticsSalesTotalByTime(commonCond: CommonCondition): Single<Result<StatisticsSalesTotalByTimeResponse>> =
        sckApi.statisticsSalesTotalByTime(
            dateFrom = commonCond.dateFrom,
            dateTo = commonCond.dateTo,
            query = commonCond.query,
            queryType = commonCond.queryType,
        )
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun createStoreReviewReply(
        reviewSeq: Long,
        request: ReviewReplyCreateRequest
    ): Single<Result<Unit>> =
        sckApi.createStoreReviewReply(
            reviewSeq,
            request,
        )
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun updateStoreReviewReply(
        replySeq: Long,
        request: ReviewReplyUpdateRequest
    ): Single<Result<Unit>> =
        sckApi.updateStoreReviewReply(
            replySeq,
            request,
        )
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun deleteStoreReviewReply(replySeq: Long): Single<Result<Unit>> =
        sckApi.deleteStoreReviewReply(replySeq)
            .toSingle {  }
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun createMenuReviewReply(
        reviewSeq: Long,
        request: ReviewReplyCreateRequest
    ): Single<Result<Unit>> =
        sckApi.createMenuReviewReply(
            reviewSeq,
            request,
        )
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun updateMenuReviewReply(
        replySeq: Long,
        request: ReviewReplyUpdateRequest
    ): Single<Result<Unit>> =
        sckApi.updateMenuReviewReply(
            replySeq,
            request,
        )
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun deleteMenuReviewReply(replySeq: Long): Single<Result<Unit>> =
        sckApi.deleteMenuReviewReply(replySeq)
            .toSingle {  }
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })
}