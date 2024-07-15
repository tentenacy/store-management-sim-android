package com.tenutz.storemngsim.data.repository.optiongroup

import com.tenutz.storemngsim.data.datasource.api.SCKApi
import com.tenutz.storemngsim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.storemngsim.data.datasource.api.dto.common.MainMenuSearchRequest
import com.tenutz.storemngsim.data.datasource.api.dto.common.OptionGroupsDeleteRequest
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.*
import com.tenutz.storemngsim.utils.constant.RetryPolicyConstant
import com.tenutz.storemngsim.utils.ext.applyRetryPolicy
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class OptionGroupRepositoryImpl @Inject constructor(
    private val sckApi: SCKApi,
) : OptionGroupRepository {
    override fun optionGroups(commonCond: CommonCondition?): Single<Result<OptionGroupsResponse>> =
        sckApi.optionGroups(commonCond?.query)
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun optionGroup(optionGroupCd: String): Single<Result<OptionGroupResponse>> =
        sckApi.optionGroup(optionGroupCd)
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun createOptionGroup(request: OptionGroupCreateRequest): Single<Result<Unit>> =
        sckApi.createOptionGroup(request)
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun updateOptionGroup(optionGroupCd: String, request: OptionGroupUpdateRequest): Single<Result<Unit>> =
        sckApi.updateOptionGroup(
            optionGroupCd,
            request
        )
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun deleteOptionGroup(optionGroupCd: String): Single<Result<Unit>> =
        sckApi.deleteOptionGroup(optionGroupCd)
            .toSingle { }
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun deleteOptionGroups(request: OptionGroupsDeleteRequest): Single<Result<Unit>> =
        sckApi.deleteOptionGroups(request)
            .toSingle { }
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun optionGroupOptions(optionGroupCd: String, commonCond: CommonCondition?): Single<Result<OptionGroupOptionsResponse>> =
        sckApi.optionGroupOptions(optionGroupCd, commonCond?.query)
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun optionGroupOptionMappers(optionGroupCd: String): Single<Result<OptionGroupOptionMappersResponse>> =
        sckApi.optionGroupOptionMappers(optionGroupCd)
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun mapToOptions(optionGroupCd: String, request: OptionsMappedByRequest): Single<Result<Unit>> =
        sckApi.mapToOptions(
            optionGroupCd,
            request,
        )
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun changeOptionGroupOptionMapperPriorities(
        optionGroupCd: String,
        request: OptionGroupOptionMapperPrioritiesChangeRequest
    ): Single<Result<Unit>> =
        sckApi.changeOptionGroupOptionMapperPriorities(
            optionGroupCd,
            request,
        )
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun deleteOptionGroupOptionMappers(
        optionGroupCd: String,
        request: OptionGroupOptionMappersDeleteRequest
    ): Single<Result<Unit>> =
        sckApi.deleteOptionGroupOptionMappers(
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
                ) { Result.failure(it) })

    override fun optionGroupMainMenus(
        optionGroupCd: String,
        request: MainMenuSearchRequest,
        commonCond: CommonCondition?,
    ): Single<Result<OptionGroupMainMenusResponse>> =
        sckApi.optionGroupMainMenus(
            optionGroupCd,
            commonCond?.query,
            request.mainCateCd,
            request.middleCateCd,
            request.subCateCd,
        )
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun optionGroupMainMenuMappers(optionGroupCd: String): Single<Result<OptionGroupMainMenuMappersResponse>> =
        sckApi.optionGroupMainMenuMappers(optionGroupCd)
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun mapToMainMenus(optionGroupCd: String, request: MainMenusMappedByRequest): Single<Result<Unit>> =
        sckApi.mapToMainMenus(
            optionGroupCd,
            request,
        )
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun changeOptionGroupMainMenuMapperPriorities(
        optionGroupCd: String,
        request: OptionGroupMainMenuMapperPrioritiesChangeRequest
    ): Single<Result<Unit>> =
        sckApi.changeOptionGroupMainMenuMapperPriorities(
            optionGroupCd,
            request,
        )
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun deleteOptionGroupMainMenuMappers(
        optionGroupCd: String,
        request: OptionGroupMainMenuMappersDeleteRequest
    ): Single<Result<Unit>> =
        sckApi.deleteOptionGroupMainMenuMappers(
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
                ) { Result.failure(it) })
}