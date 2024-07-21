package com.tenutz.storemngsim.data.datasource.api

import com.tenutz.storemngsim.data.datasource.api.dto.terms.TermsDetailsResponse
import com.tenutz.storemngsim.data.datasource.api.dto.terms.TermsResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface TermsApi {

    @GET("/terms")
    fun terms(): Single<TermsResponse>

    @GET("/terms/{termsCd}")
    fun termsDetails(@Path("termsCd") termsCd: String): Single<TermsDetailsResponse>
}