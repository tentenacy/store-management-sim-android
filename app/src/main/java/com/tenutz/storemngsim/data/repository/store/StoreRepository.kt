package com.tenutz.storemngsim.data.repository.store

import com.tenutz.storemngsim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.storemngsim.data.datasource.api.dto.store.ReviewReplyCreateRequest
import com.tenutz.storemngsim.data.datasource.api.dto.store.ReviewReplyUpdateRequest
import com.tenutz.storemngsim.data.datasource.api.dto.store.SalesRequest
import com.tenutz.storemngsim.data.datasource.api.dto.store.SalesTotalResponse
import com.tenutz.storemngsim.data.datasource.api.dto.store.StatisticsSaleByMenusRequest
import com.tenutz.storemngsim.data.datasource.api.dto.store.StatisticsSalesByCreditCardResponse
import com.tenutz.storemngsim.data.datasource.api.dto.store.StatisticsSalesByMenusTodayResponse
import com.tenutz.storemngsim.data.datasource.api.dto.store.StatisticsSalesByTimeResponse
import com.tenutz.storemngsim.data.datasource.api.dto.store.StatisticsSalesTotalByCreditCardResponse
import com.tenutz.storemngsim.data.datasource.api.dto.store.StatisticsSalesTotalByMenusResponse
import com.tenutz.storemngsim.data.datasource.api.dto.store.StatisticsSalesTotalByTimeResponse
import com.tenutz.storemngsim.data.datasource.api.dto.store.StoreMainResponse
import io.reactivex.rxjava3.core.Single

interface StoreRepository {

    fun salesTotal(
        commonCond: CommonCondition,
        request: SalesRequest,
    ): Single<Result<SalesTotalResponse>>

    fun statisticsSalesTotalByMenu(
        commonCond: CommonCondition,
        request: StatisticsSaleByMenusRequest? = null,
    ): Single<Result<StatisticsSalesTotalByMenusResponse>>

    fun statisticsSalesByMenuToday(): Single<Result<StatisticsSalesByMenusTodayResponse>>

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

    fun storeMain(): Single<Result<StoreMainResponse>>
}