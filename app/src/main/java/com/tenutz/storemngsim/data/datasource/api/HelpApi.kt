package com.tenutz.storemngsim.data.datasource.api

import com.tenutz.storemngsim.data.datasource.api.dto.help.HelpsResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface HelpApi {

    @GET("/helps")
    fun helps(
        @Query("query") query: String? = null,
    ): Single<HelpsResponse>
}