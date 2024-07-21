package com.tenutz.storemngsim.data.repository.terms

import com.tenutz.storemngsim.data.datasource.api.SMSApi
import com.tenutz.storemngsim.data.datasource.api.dto.terms.TermsDetailsResponse
import com.tenutz.storemngsim.data.datasource.api.dto.terms.TermsResponse
import com.tenutz.storemngsim.utils.constant.RetryPolicyConstant
import com.tenutz.storemngsim.utils.ext.applyRetryPolicy
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class TermsRepositoryImpl @Inject constructor(
    private val SMSApi: SMSApi,
): TermsRepository {

    override fun terms(): Single<Result<TermsResponse>> = SMSApi.terms()
        .map { Result.success(it) }
        .compose(
            applyRetryPolicy(
                RetryPolicyConstant.TIMEOUT,
                RetryPolicyConstant.NETWORK,
                RetryPolicyConstant.SERVICE_UNAVAILABLE,
            ) { Result.failure(it) })

    override fun termsDetails(termsCd: String): Single<Result<TermsDetailsResponse>> = SMSApi.termsDetails(termsCd)
        .map { Result.success(it) }
        .compose(
            applyRetryPolicy(
                RetryPolicyConstant.TIMEOUT,
                RetryPolicyConstant.NETWORK,
                RetryPolicyConstant.SERVICE_UNAVAILABLE,
            ) { Result.failure(it) })
}