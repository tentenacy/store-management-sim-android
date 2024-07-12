package com.tenutz.storemngsim.data.repository.store

import com.tenutz.storemngsim.data.datasource.api.SCKApi
import com.tenutz.storemngsim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.storemngsim.data.datasource.api.dto.store.*
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class StoreRepositoryImpl @Inject constructor(
    private val sckApi: SCKApi,
) : StoreRepository {
    override fun equipments(): Single<EquipmentsResponse> =
        sckApi.equipments()

    override fun salesTotal(
        commonCond: CommonCondition,
        request: SalesRequest
    ): Single<SalesTotalResponse> =
        sckApi.salesTotal(
            dateFrom = commonCond.dateFrom,
            dateTo = commonCond.dateTo,
            query = commonCond.query,
            queryType = commonCond.queryType,
            paymentType = request.paymentType,
            approvalType = request.approvalType,
            orderType = request.orderType,
        )

    override fun statisticsSalesTotalByMenu(
        commonCond: CommonCondition,
        request: StatisticsSaleByMenusRequest
    ): Single<StatisticsSalesTotalByMenusResponse> =
        sckApi.statisticsSalesTotalByMenu(
            dateFrom = commonCond.dateFrom,
            dateTo = commonCond.dateTo,
            query = commonCond.query,
            queryType = commonCond.queryType,
            mainCategoryCode = request.mainCategoryCode,
        )

    override fun statisticsSalesByCreditCard(commonCond: CommonCondition): Single<StatisticsSalesByCreditCardResponse> =
        sckApi.statisticsSalesByCreditCard(
            dateFrom = commonCond.dateFrom,
            dateTo = commonCond.dateTo,
            query = commonCond.query,
            queryType = commonCond.queryType,
        )

    override fun statisticsSalesTotalByCreditCard(commonCond: CommonCondition): Single<StatisticsSalesTotalByCreditCardResponse> =
        sckApi.statisticsSalesTotalByCreditCard(
            dateFrom = commonCond.dateFrom,
            dateTo = commonCond.dateTo,
            query = commonCond.query,
            queryType = commonCond.queryType,
        )

    override fun statisticsSalesTotalByTime(commonCond: CommonCondition): Single<StatisticsSalesTotalByTimeResponse> =
        sckApi.statisticsSalesTotalByTime(
            dateFrom = commonCond.dateFrom,
            dateTo = commonCond.dateTo,
            query = commonCond.query,
            queryType = commonCond.queryType,
        )

    override fun createStoreReviewReply(
        reviewSeq: Long,
        request: ReviewReplyCreateRequest
    ): Single<Unit> =
        sckApi.createStoreReviewReply(
            reviewSeq,
            request,
        )

    override fun updateStoreReviewReply(
        replySeq: Long,
        request: ReviewReplyUpdateRequest
    ): Single<Unit> =
        sckApi.updateStoreReviewReply(
            replySeq,
            request,
        )

    override fun deleteStoreReviewReply(replySeq: Long): Single<Unit> =
        sckApi.deleteStoreReviewReply(replySeq)

    override fun createMenuReviewReply(
        reviewSeq: Long,
        request: ReviewReplyCreateRequest
    ): Single<Unit> =
        sckApi.createMenuReviewReply(
            reviewSeq,
            request,
        )

    override fun updateMenuReviewReply(
        replySeq: Long,
        request: ReviewReplyUpdateRequest
    ): Single<Unit> =
        sckApi.updateMenuReviewReply(
            replySeq,
            request,
        )

    override fun deleteMenuReviewReply(replySeq: Long): Single<Unit> =
        sckApi.deleteMenuReviewReply(replySeq)
}