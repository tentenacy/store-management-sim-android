package com.tenutz.storemngsim.data.repository.option

import com.tenutz.storemngsim.data.datasource.api.SMSApi
import com.tenutz.storemngsim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.storemngsim.data.datasource.api.dto.common.OptionGroupPrioritiesChangeRequest
import com.tenutz.storemngsim.data.datasource.api.dto.common.OptionGroupsDeleteRequest
import com.tenutz.storemngsim.data.datasource.api.dto.common.OptionGroupsMappedByRequest
import com.tenutz.storemngsim.data.datasource.api.dto.option.*
import com.tenutz.storemngsim.utils.constant.RetryPolicyConstant
import com.tenutz.storemngsim.utils.ext.applyRetryPolicy
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class OptionRepositoryImpl @Inject constructor(
    private val sckApi: SMSApi,
) : OptionRepository {
    override fun options(commonCond: CommonCondition?): Single<Result<OptionsResponse>> =
        sckApi.options(commonCond?.query)
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun option(optionCd: String): Single<Result<OptionResponse>> =
        sckApi.option(optionCd)
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun createOption(request: OptionCreateRequest): Single<Result<Unit>> =
        sckApi.createOption(
            request.image,
            request.optionCode,
            request.optionName,
            request.price,
            request.discountedPrice,
            request.additionalPackagingPrice,
            request.packaging,
            request.outOfStock,
            request.use,
            request.optionNameKor,
            request.showDateFrom,
            request.showDateTo,
            request.showTimeFrom,
            request.showTimeTo,
            request.showDayOfWeek,
            request.eventDateFrom,
            request.eventDateTo,
            request.eventTimeFrom,
            request.eventTimeTo,
            request.eventDayOfWeek,
        )
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun updateOption(optionCd: String, request: OptionUpdateRequest): Single<Result<Unit>> =
        sckApi.updateOption(
            optionCd,
            request.image,
            request.optionName,
            request.price,
            request.discountedPrice,
            request.additionalPackagingPrice,
            request.packaging,
            request.outOfStock,
            request.use,
            request.optionNameKor,
            request.showDateFrom,
            request.showDateTo,
            request.showTimeFrom,
            request.showTimeTo,
            request.showDayOfWeek,
            request.eventDateFrom,
            request.eventDateTo,
            request.eventTimeFrom,
            request.eventTimeTo,
            request.eventDayOfWeek,
        )
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun deleteOption(optionCd: String): Single<Result<Unit>> =
        sckApi.deleteOption(optionCd)
            .toSingle { }
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun deleteOptions(request: OptionsDeleteRequest): Single<Result<Unit>> =
        sckApi.deleteOptions(request)
            .toSingle { }
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun optionOptionGroups(optionCd: String): Single<Result<OptionOptionGroupsResponse>> =
        sckApi.optionOptionGroups(optionCd)
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun optionMappers(optionCd: String): Single<Result<OptionMappersResponse>> =
        sckApi.optionMappers(optionCd)
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun mapToOptionGroups(
        optionCd: String,
        request: OptionGroupsMappedByRequest
    ): Single<Result<Unit>> =
        sckApi.mapToOptionGroups(
            optionCd,
            request,
        )
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun deleteOptionMappers(
        optionCd: String,
        request: OptionGroupsDeleteRequest
    ): Single<Result<Unit>> =
        sckApi.deleteOptionMappers(
            optionCd,
            request,
        )
            .toSingle { }
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun changeOptionMapperPriorities(
        optionCd: String,
        request: OptionGroupPrioritiesChangeRequest
    ): Single<Result<Unit>> =
        sckApi.changeOptionMapperPriorities(
            optionCd,
            request,
        )
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })
}