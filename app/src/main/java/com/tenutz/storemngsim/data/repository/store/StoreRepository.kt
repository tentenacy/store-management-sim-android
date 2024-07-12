package com.tenutz.storemngsim.data.repository.store

import com.tenutz.storemngsim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.storemngsim.data.datasource.api.dto.store.*
import io.reactivex.rxjava3.core.Single

interface StoreRepository {

    fun equipments(): Single<EquipmentsResponse>

    fun salesTotal(
        commonCond: CommonCondition,
        request: SalesRequest,
    ): Single<SalesTotalResponse>

    fun statisticsSalesTotalByMenu(
        commonCond: CommonCondition,
        request: StatisticsSaleByMenusRequest,
    ): Single<StatisticsSalesTotalByMenusResponse>

    fun statisticsSalesByCreditCard(
        commonCond: CommonCondition,
    ): Single<StatisticsSalesByCreditCardResponse>

    fun statisticsSalesTotalByCreditCard(
        commonCond: CommonCondition,
    ): Single<StatisticsSalesTotalByCreditCardResponse>

    fun statisticsSalesTotalByTime(
        commonCond: CommonCondition,
    ): Single<StatisticsSalesTotalByTimeResponse>

    fun createStoreReviewReply(
        reviewSeq: Long,
        request: ReviewReplyCreateRequest,
    ): Single<Unit>

    fun updateStoreReviewReply(
        replySeq: Long,
        request: ReviewReplyUpdateRequest,
    ): Single<Unit>

    fun deleteStoreReviewReply(replySeq: Long): Single<Unit>

    fun createMenuReviewReply(
        reviewSeq: Long,
        request: ReviewReplyCreateRequest,
    ): Single<Unit>

    fun updateMenuReviewReply(
        replySeq: Long,
        request: ReviewReplyUpdateRequest,
    ): Single<Unit>

    fun deleteMenuReviewReply(replySeq: Long): Single<Unit>
}