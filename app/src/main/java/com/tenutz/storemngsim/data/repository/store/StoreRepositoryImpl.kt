package com.tenutz.storemngsim.data.repository.store

import com.tenutz.storemngsim.data.datasource.api.SMSApi
import com.tenutz.storemngsim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.storemngsim.data.datasource.api.dto.store.*
import com.tenutz.storemngsim.utils.constant.RetryPolicyConstant
import com.tenutz.storemngsim.utils.ext.applyRetryPolicy
import com.tenutz.storemngsim.utils.ext.toDateTimeFormat
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class StoreRepositoryImpl @Inject constructor(
    private val SMSApi: SMSApi,
) : StoreRepository {
    override fun equipments(): Single<Result<EquipmentsResponse>> =
        SMSApi.equipments()
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
        SMSApi.salesTotal(
            dateFrom = commonCond.dateFrom.toDateTimeFormat(),
            dateTo = commonCond.dateTo.toDateTimeFormat(),
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
        request: StatisticsSaleByMenusRequest?,
    ): Single<Result<StatisticsSalesTotalByMenusResponse>> =
        SMSApi.statisticsSalesTotalByMenu(
            dateFrom = commonCond.dateFrom.toDateTimeFormat(),
            dateTo = commonCond.dateTo.toDateTimeFormat(),
            query = commonCond.query,
            queryType = commonCond.queryType,
            mainCategoryCode = request?.mainCategoryCode,
        )
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun statisticsSalesByMenuToday(): Single<Result<StatisticsSalesByMenusTodayResponse>> =
        SMSApi.statisticsSalesByMenuToday()
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun statisticsSalesByCreditCard(commonCond: CommonCondition): Single<Result<StatisticsSalesByCreditCardResponse>> =
        SMSApi.statisticsSalesByCreditCard(
            dateFrom = commonCond.dateFrom.toDateTimeFormat(),
            dateTo = commonCond.dateTo.toDateTimeFormat(),
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
        SMSApi.statisticsSalesTotalByCreditCard(
            dateFrom = commonCond.dateFrom.toDateTimeFormat(),
            dateTo = commonCond.dateTo.toDateTimeFormat(),
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

    override fun statisticsSalesByTime(commonCond: CommonCondition): Single<Result<StatisticsSalesByTimeResponse>> =
        SMSApi.statisticsSalesByTime(
            dateFrom = commonCond.dateFrom.toDateTimeFormat(),
            dateTo = commonCond.dateTo.toDateTimeFormat(),
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
        SMSApi.statisticsSalesTotalByTime(
            dateFrom = commonCond.dateFrom.toDateTimeFormat(),
            dateTo = commonCond.dateTo.toDateTimeFormat(),
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
        SMSApi.createStoreReviewReply(
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
        SMSApi.updateStoreReviewReply(
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
        SMSApi.deleteStoreReviewReply(replySeq)
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
        SMSApi.createMenuReviewReply(
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
        SMSApi.updateMenuReviewReply(
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
        SMSApi.deleteMenuReviewReply(replySeq)
            .toSingle {  }
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun storeMain(): Single<Result<StoreMainResponse>> = SMSApi.storeMain()
        .map { Result.success(it) }
        .compose(
            applyRetryPolicy(
                RetryPolicyConstant.TIMEOUT,
                RetryPolicyConstant.NETWORK,
                RetryPolicyConstant.SERVICE_UNAVAILABLE,
            ) { Result.failure(it) })
}