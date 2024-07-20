package com.tenutz.storemngsim.data.repository.store

import com.tenutz.storemngsim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.storemngsim.data.datasource.api.dto.store.*
import io.reactivex.rxjava3.core.Single

interface StoreRepository {

    fun equipments(): Single<Result<EquipmentsResponse>>

    fun salesTotal(
        commonCond: CommonCondition,
        request: SalesRequest,
    ): Single<Result<SalesTotalResponse>>

    fun statisticsSalesTotalByMenu(
        commonCond: CommonCondition,
        request: StatisticsSaleByMenusRequest? = null,
    ): Single<Result<StatisticsSalesTotalByMenusResponse>>

    fun statisticsSalesByCreditCard(
        commonCond: CommonCondition,
    ): Single<Result<StatisticsSalesByCreditCardResponse>>

    fun statisticsSalesTotalByCreditCard(
        commonCond: CommonCondition,
    ): Single<Result<StatisticsSalesTotalByCreditCardResponse>>

    fun statisticsSalesByTime(
        commonCond: CommonCondition,
    ): Single<Result<StatisticsSalesByTimeResponse>>

    fun statisticsSalesTotalByTime(
        commonCond: CommonCondition,
    ): Single<Result<StatisticsSalesTotalByTimeResponse>>

    fun createStoreReviewReply(
        reviewSeq: Long,
        request: ReviewReplyCreateRequest,
    ): Single<Result<Unit>>

    fun updateStoreReviewReply(
        replySeq: Long,
        request: ReviewReplyUpdateRequest,
    ): Single<Result<Unit>>

    fun deleteStoreReviewReply(replySeq: Long): Single<Result<Unit>>

    fun createMenuReviewReply(
        reviewSeq: Long,
        request: ReviewReplyCreateRequest,
    ): Single<Result<Unit>>

    fun updateMenuReviewReply(
        replySeq: Long,
        request: ReviewReplyUpdateRequest,
    ): Single<Result<Unit>>

    fun deleteMenuReviewReply(replySeq: Long): Single<Result<Unit>>
}