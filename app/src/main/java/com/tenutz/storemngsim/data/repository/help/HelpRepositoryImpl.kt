package com.tenutz.storemngsim.data.repository.help

import com.tenutz.storemngsim.data.datasource.api.SMSApi
import com.tenutz.storemngsim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.storemngsim.data.datasource.api.dto.help.HelpsResponse
import com.tenutz.storemngsim.utils.constant.RetryPolicyConstant
import com.tenutz.storemngsim.utils.ext.applyRetryPolicy
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class HelpRepositoryImpl @Inject constructor(
    private val SMSApi: SMSApi,
): HelpRepository {

    override fun helps(commonCond: CommonCondition?): Single<Result<HelpsResponse>> =
        SMSApi.helps(commonCond?.query)
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })
}