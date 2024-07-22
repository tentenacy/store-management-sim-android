package com.tenutz.storemngsim.data.datasource.api

import com.tenutz.storemngsim.data.datasource.api.dto.common.PageResponse
import com.tenutz.storemngsim.data.datasource.api.dto.push.PushAlarmsResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PushApi {

    @GET("/push")
    fun pushAlarms(
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("sort") sort: String? = "createDate,desc",
        @Query("dateFrom") dateFrom: String? = null,
        @Query("dateTo") dateTo: String? = null,
        @Query("query") query: String? = null,
        @Query("queryType") queryType: String? = null,
    ): Single<PageResponse<PushAlarmsResponse>>
}