package com.tenutz.storemngsim.data.datasource.api

import com.tenutz.storemngsim.data.datasource.api.dto.common.PageResponse
import com.tenutz.storemngsim.data.datasource.api.dto.store.*
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*
import java.util.*

interface StoreApi {

    @GET("/stores/equipments")
    fun equipments(): Single<EquipmentsResponse>

    @GET("/stores/sales")
    fun sales(
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("sort") sort: String? = null,
        @Query("dateFrom") dateFrom: Date? = null,
        @Query("dateTo") dateTo: Date? = null,
        @Query("query") query: String? = null,
        @Query("queryType") queryType: String? = null,
        @Query("paymentType") paymentType: String? = null,
        @Query("approvalType") approvalType: String? = null,
        @Query("orderType") orderType: String? = null,
    ): Single<PageResponse<SalesResponse>>

    @GET("/stores/sales/total")
    fun salesTotal(
        @Query("dateFrom") dateFrom: Date? = null,
        @Query("dateTo") dateTo: Date? = null,
        @Query("query") query: String? = null,
        @Query("queryType") queryType: String? = null,
        @Query("paymentType") paymentType: String? = null,
        @Query("approvalType") approvalType: String? = null,
        @Query("orderType") orderType: String? = null,
    ): Single<SalesTotalResponse>

    @GET("/stores/statistics/sales-by-menu")
    fun statisticsSalesByMenu(
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("sort") sort: String? = null,
        @Query("dateFrom") dateFrom: Date? = null,
        @Query("dateTo") dateTo: Date? = null,
        @Query("query") query: String? = null,
        @Query("queryType") queryType: String? = null,
        @Query("mainCategoryCode") mainCategoryCode: String? = null,
    ): Single<PageResponse<StatisticsSalesByMenusResponse>>

    @GET("/stores/statistics/sales-by-menu/total")
    fun statisticsSalesTotalByMenu(
        @Query("dateFrom") dateFrom: Date? = null,
        @Query("dateTo") dateTo: Date? = null,
        @Query("query") query: String? = null,
        @Query("queryType") queryType: String? = null,
        @Query("mainCategoryCode") mainCategoryCode: String? = null,
    ): Single<StatisticsSalesTotalByMenusResponse>

    @GET("/stores/statistics/sales-by-card")
    fun statisticsSalesByCreditCard(
        @Query("dateFrom") dateFrom: Date? = null,
        @Query("dateTo") dateTo: Date? = null,
        @Query("query") query: String? = null,
        @Query("queryType") queryType: String? = null,
    ): Single<StatisticsSalesByCreditCardResponse>

    @GET("/stores/statistics/sales-by-card/total")
    fun statisticsSalesTotalByCreditCard(
        @Query("dateFrom") dateFrom: Date? = null,
        @Query("dateTo") dateTo: Date? = null,
        @Query("query") query: String? = null,
        @Query("queryType") queryType: String? = null,
    ): Single<StatisticsSalesTotalByCreditCardResponse>

    @GET("/stores/statistics/sales-by-time")
    fun statisticsSalesByTime(
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("sort") sort: String? = null,
        @Query("dateFrom") dateFrom: Date? = null,
        @Query("dateTo") dateTo: Date? = null,
        @Query("query") query: String? = null,
        @Query("queryType") queryType: String? = null,
    ): Single<PageResponse<StatisticsSalesByTimeResponse>>

    @GET("/stores/statistics/sales-by-time/total")
    fun statisticsSalesTotalByTime(
        @Query("dateFrom") dateFrom: Date? = null,
        @Query("dateTo") dateTo: Date? = null,
        @Query("query") query: String? = null,
        @Query("queryType") queryType: String? = null,
    ): Single<StatisticsSalesTotalByTimeResponse>

    @GET("/stores/reviews")
    fun storeReviews(
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("sort") sort: String? = null,
        @Query("dateFrom") dateFrom: Date? = null,
        @Query("dateTo") dateTo: Date? = null,
        @Query("query") query: String? = null,
        @Query("queryType") queryType: String? = null,
    ): Single<PageResponse<StoreReviewsResponse>>

    @POST("/stores/reviews/{reviewSeq}/replies")
    fun createStoreReviewReply(
        @Path("reviewSeq") reviewSeq: Long,
        @Body request: ReviewReplyCreateRequest
    ): Single<Unit>

    @PUT("/stores/reviews/replies/{replySeq}")
    fun updateStoreReviewReply(
        @Path("replySeq") replySeq: Long,
        @Body request: ReviewReplyUpdateRequest
    ): Single<Unit>

    @DELETE("/stores/reviews/replies/{replySeq}")
    fun deleteStoreReviewReply(@Path("replySeq") replySeq: Long): Single<Unit>

    @GET("/stores/menus/reviews")
    fun menuReviews(
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("sort") sort: String? = null,
        @Query("dateFrom") dateFrom: Date? = null,
        @Query("dateTo") dateTo: Date? = null,
        @Query("query") query: String? = null,
        @Query("queryType") queryType: String? = null,
    ): Single<PageResponse<MenuReviewsResponse>>

    @POST("/stores/menus/reviews/{reviewSeq}/replies")
    fun createMenuReviewReply(
        @Path("reviewSeq") reviewSeq: Long,
        @Body request: ReviewReplyCreateRequest
    ): Single<Unit>

    @PUT("/stores/menus/reviews/replies/{replySeq}")
    fun updateMenuReviewReply(
        @Path("replySeq") replySeq: Long,
        @Body request: ReviewReplyUpdateRequest
    ): Single<Unit>

    @DELETE("/stores/menus/reviews/replies/{replySeq}")
    fun deleteMenuReviewReply(@Path("replySeq") replySeq: Long): Single<Unit>
}