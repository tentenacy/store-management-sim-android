package com.tenutz.storemngsim.data.repository.optiongroup

import com.tenutz.storemngsim.data.datasource.api.SMSApi
import com.tenutz.storemngsim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.storemngsim.data.datasource.api.dto.common.MainMenuSearchRequest
import com.tenutz.storemngsim.data.datasource.api.dto.common.OptionGroupsDeleteRequest
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.MainMenusMappedByRequest
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.OptionGroupCreateRequest
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.OptionGroupMainMenuMapperPrioritiesChangeRequest
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.OptionGroupMainMenuMappersDeleteRequest
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.OptionGroupMainMenuMappersResponse
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.OptionGroupMainMenusResponse
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.OptionGroupOptionMapperPrioritiesChangeRequest
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.OptionGroupOptionMappersDeleteRequest
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.OptionGroupOptionMappersResponse
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.OptionGroupOptionsResponse
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.OptionGroupResponse
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.OptionGroupUpdateRequest
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.OptionGroupsResponse
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.OptionsMappedByRequest
import com.tenutz.storemngsim.utils.constant.RetryPolicyConstant
import com.tenutz.storemngsim.utils.ext.applyRetryPolicy
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class OptionGroupRepositoryImpl @Inject constructor(
    private val SMSApi: SMSApi,
) : OptionGroupRepository {
    override fun optionGroups(commonCond: CommonCondition?): Single<Result<OptionGroupsResponse>> =
        SMSApi.optionGroups(commonCond?.query)
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                    RetryPolicyConstant.ACCESS_TOKEN_EXPIRED,
                ) { Result.failure(it) })

    override fun optionGroup(optionGroupCd: String): Single<Result<OptionGroupResponse>> =
        SMSApi.optionGroup(optionGroupCd)
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                    RetryPolicyConstant.ACCESS_TOKEN_EXPIRED,
                ) { Result.failure(it) })

    override fun createOptionGroup(request: OptionGroupCreateRequest): Single<Result<Unit>> =
        SMSApi.createOptionGroup(request)
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                    RetryPolicyConstant.ACCESS_TOKEN_EXPIRED,
                ) { Result.failure(it) })

    override fun updateOptionGroup(optionGroupCd: String, request: OptionGroupUpdateRequest): Single<Result<Unit>> =
        SMSApi.updateOptionGroup(
            optionGroupCd,
            request
        )
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                    RetryPolicyConstant.ACCESS_TOKEN_EXPIRED,
                ) { Result.failure(it) })

    override fun deleteOptionGroup(optionGroupCd: String): Single<Result<Unit>> =
        SMSApi.deleteOptionGroup(optionGroupCd)
            .toSingle { }
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                    RetryPolicyConstant.ACCESS_TOKEN_EXPIRED,
                ) { Result.failure(it) })

    override fun deleteOptionGroups(request: OptionGroupsDeleteRequest): Single<Result<Unit>> =
        SMSApi.deleteOptionGroups(request)
            .toSingle { }
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                    RetryPolicyConstant.ACCESS_TOKEN_EXPIRED,
                ) { Result.failure(it) })

    override fun optionGroupOptions(optionGroupCd: String, commonCond: CommonCondition?): Single<Result<OptionGroupOptionsResponse>> =
        SMSApi.optionGroupOptions(optionGroupCd, commonCond?.query)
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                    RetryPolicyConstant.ACCESS_TOKEN_EXPIRED,
                ) { Result.failure(it) })

    override fun optionGroupOptionMappers(optionGroupCd: String): Single<Result<OptionGroupOptionMappersResponse>> =
        SMSApi.optionGroupOptionMappers(optionGroupCd)
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                    RetryPolicyConstant.ACCESS_TOKEN_EXPIRED,
                ) { Result.failure(it) })

    override fun mapToOptions(optionGroupCd: String, request: OptionsMappedByRequest): Single<Result<Unit>> =
        SMSApi.mapToOptions(
            optionGroupCd,
            request,
        )
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                    RetryPolicyConstant.ACCESS_TOKEN_EXPIRED,
                ) { Result.failure(it) })

    override fun changeOptionGroupOptionMapperPriorities(
        optionGroupCd: String,
        request: OptionGroupOptionMapperPrioritiesChangeRequest
    ): Single<Result<Unit>> =
        SMSApi.changeOptionGroupOptionMapperPriorities(
            optionGroupCd,
            request,
        )
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                    RetryPolicyConstant.ACCESS_TOKEN_EXPIRED,
                ) { Result.failure(it) })

    override fun deleteOptionGroupOptionMappers(
        optionGroupCd: String,
        request: OptionGroupOptionMappersDeleteRequest
    ): Single<Result<Unit>> =
        SMSApi.deleteOptionGroupOptionMappers(
            optionGroupCd,
            request,
        )
            .toSingle { }
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                    RetryPolicyConstant.ACCESS_TOKEN_EXPIRED,
                ) { Result.failure(it) })

    override fun optionGroupMainMenus(
        optionGroupCd: String,
        request: MainMenuSearchRequest,
        commonCond: CommonCondition?,
    ): Single<Result<OptionGroupMainMenusResponse>> =
        SMSApi.optionGroupMainMenus(
            optionGroupCd,
            commonCond?.query,
            request.mainCateCd,
            request.middleCateCd ?: "0000",
            request.subCateCd ?: "0000",
        )
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                    RetryPolicyConstant.ACCESS_TOKEN_EXPIRED,
                ) { Result.failure(it) })

    override fun optionGroupMainMenuMappers(optionGroupCd: String): Single<Result<OptionGroupMainMenuMappersResponse>> =
        SMSApi.optionGroupMainMenuMappers(optionGroupCd)
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                    RetryPolicyConstant.ACCESS_TOKEN_EXPIRED,
                ) { Result.failure(it) })

    override fun mapToMainMenus(optionGroupCd: String, request: MainMenusMappedByRequest): Single<Result<Unit>> =
        SMSApi.mapToMainMenus(
            optionGroupCd,
            request,
        )
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                    RetryPolicyConstant.ACCESS_TOKEN_EXPIRED,
                ) { Result.failure(it) })

    override fun changeOptionGroupMainMenuMapperPriorities(
        optionGroupCd: String,
        request: OptionGroupMainMenuMapperPrioritiesChangeRequest
    ): Single<Result<Unit>> =
        SMSApi.changeOptionGroupMainMenuMapperPriorities(
            optionGroupCd,
            request,
        )
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                    RetryPolicyConstant.ACCESS_TOKEN_EXPIRED,
                ) { Result.failure(it) })

    override fun deleteOptionGroupMainMenuMappers(
        optionGroupCd: String,
        request: OptionGroupMainMenuMappersDeleteRequest
    ): Single<Result<Unit>> =
        SMSApi.deleteOptionGroupMainMenuMappers(
            optionGroupCd,
            request,
        )
            .toSingle { }
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                    RetryPolicyConstant.ACCESS_TOKEN_EXPIRED,
                ) { Result.failure(it) })
}