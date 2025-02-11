package com.tenutz.storemngsim.data.datasource.api

import com.tenutz.storemngsim.data.datasource.api.dto.common.PageResponse
import com.tenutz.storemngsim.data.datasource.api.dto.store.MenuReviewsResponse
import com.tenutz.storemngsim.data.datasource.api.dto.store.ReviewReplyCreateRequest
import com.tenutz.storemngsim.data.datasource.api.dto.store.ReviewReplyUpdateRequest
import com.tenutz.storemngsim.data.datasource.api.dto.store.SalesResponse
import com.tenutz.storemngsim.data.datasource.api.dto.store.SalesTotalResponse
import com.tenutz.storemngsim.data.datasource.api.dto.store.StatisticsSalesByCreditCardResponse
import com.tenutz.storemngsim.data.datasource.api.dto.store.StatisticsSalesByMenusResponse
import com.tenutz.storemngsim.data.datasource.api.dto.store.StatisticsSalesByMenusTodayResponse
import com.tenutz.storemngsim.data.datasource.api.dto.store.StatisticsSalesByTimeResponse
import com.tenutz.storemngsim.data.datasource.api.dto.store.StatisticsSalesTotalByCreditCardResponse
import com.tenutz.storemngsim.data.datasource.api.dto.store.StatisticsSalesTotalByMenusResponse
import com.tenutz.storemngsim.data.datasource.api.dto.store.StatisticsSalesTotalByTimeResponse
import com.tenutz.storemngsim.data.datasource.api.dto.store.StoreMainResponse
import com.tenutz.storemngsim.data.datasource.api.dto.store.StoreReviewsResponse
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface StoreApi {

    @GET("/stores/sales")
    fun sales(
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("sort") sort: String? = "saleDt,desc",
        @Query("dateFrom") dateFrom: String? = null,
        @Query("dateTo") dateTo: String? = null,
        @Query("query") query: String? = null,
        @Query("queryType") queryType: String? = null,
        @Query("paymentType") paymentType: String? = null,
        @Query("approvalType") approvalType: String? = null,
        @Query("orderType") orderType: String? = null,
    ): Single<PageResponse<SalesResponse>>

    @GET("/stores/sales/total")
    fun salesTotal(
        @Query("dateFrom") dateFrom: String? = null,
        @Query("dateTo") dateTo: String? = null,
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
        @Query("dateFrom") dateFrom: String? = null,
        @Query("dateTo") dateTo: String? = null,
        @Query("query") query: String? = null,
        @Query("queryType") queryType: String? = null,
        @Query("mainCategoryCode") mainCategoryCode: String? = null,
    ): Single<PageResponse<StatisticsSalesByMenusResponse>>

    @GET("/stores/statistics/sales-by-menu/total")
    fun statisticsSalesTotalByMenu(
        @Query("dateFrom") dateFrom: String? = null,
        @Query("dateTo") dateTo: String? = null,
        @Query("query") query: String? = null,
        @Query("queryType") queryType: String? = null,
        @Query("mainCategoryCode") mainCategoryCode: String? = null,
    ): Single<StatisticsSalesTotalByMenusResponse>

    @GET("/stores/statistics/sales-by-menu/today")
    fun statisticsSalesByMenuToday(): Single<StatisticsSalesByMenusTodayResponse>

    @GET("/stores/statistics/sales-by-card")
    fun statisticsSalesByCreditCard(
        @Query("dateFrom") dateFrom: String? = null,
        @Query("dateTo") dateTo: String? = null,
        @Query("query") query: String? = null,
        @Query("queryType") queryType: String? = null,
    ): Single<StatisticsSalesByCreditCardResponse>

    @GET("/stores/statistics/sales-by-card/total")
    fun statisticsSalesTotalByCreditCard(
        @Query("dateFrom") dateFrom: String? = null,
        @Query("dateTo") dateTo: String? = null,
        @Query("query") query: String? = null,
        @Query("queryType") queryType: String? = null,
    ): Single<StatisticsSalesTotalByCreditCardResponse>

    @GET("/stores/statistics/sales-by-time")
    fun statisticsSalesByTime(
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("sort") sort: String? = null,
        @Query("dateFrom") dateFrom: String? = null,
        @Query("dateTo") dateTo: String? = null,
        @Query("query") query: String? = null,
        @Query("queryType") queryType: String? = null,
    ): Single<StatisticsSalesByTimeResponse>

    @GET("/stores/statistics/sales-by-time/total")
    fun statisticsSalesTotalByTime(
        @Query("dateFrom") dateFrom: String? = null,
        @Query("dateTo") dateTo: String? = null,
        @Query("query") query: String? = null,
        @Query("queryType") queryType: String? = null,
    ): Single<StatisticsSalesTotalByTimeResponse>

    @GET("/stores/reviews")
    fun storeReviews(
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("sort") sort: String? = "createdDttm,desc",
        @Query("dateFrom") dateFrom: String? = null,
        @Query("dateTo") dateTo: String? = null,
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

    @HTTP(method = "DELETE", path = "/stores/reviews/replies/{replySeq}", hasBody = true)
    fun deleteStoreReviewReply(@Path("replySeq") replySeq: Long): Completable

    @GET("/stores/menus/reviews")
    fun menuReviews(
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("sort") sort: String? = "createdDttm,desc",
        @Query("dateFrom") dateFrom: String? = null,
        @Query("dateTo") dateTo: String? = null,
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

    @HTTP(method = "DELETE", path = "/stores/menus/reviews/replies/{replySeq}", hasBody = true)
    fun deleteMenuReviewReply(@Path("replySeq") replySeq: Long): Completable

    @GET("/stores/main")
    fun storeMain(): Single<StoreMainResponse>
}