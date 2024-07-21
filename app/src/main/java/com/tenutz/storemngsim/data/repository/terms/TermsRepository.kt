package com.tenutz.storemngsim.data.repository.terms

import com.tenutz.storemngsim.data.datasource.api.dto.terms.TermsDetailsResponse
import com.tenutz.storemngsim.data.datasource.api.dto.terms.TermsResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Path

interface TermsRepository {

    fun terms(): Single<Result<TermsResponse>>

    fun termsDetails(termsCd: String): Single<Result<TermsDetailsResponse>>
}