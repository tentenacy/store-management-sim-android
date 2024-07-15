package com.tenutz.storemngsim.data.repository.optiongroup

import com.tenutz.storemngsim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.storemngsim.data.datasource.api.dto.common.MainMenuSearchRequest
import com.tenutz.storemngsim.data.datasource.api.dto.common.OptionGroupsDeleteRequest
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.*
import io.reactivex.rxjava3.core.Single

interface OptionGroupRepository {

    fun optionGroups(commonCond: CommonCondition? = null): Single<Result<OptionGroupsResponse>>

    fun optionGroup(optionGroupCd: String): Single<Result<OptionGroupResponse>>

    fun createOptionGroup(request: OptionGroupCreateRequest): Single<Result<Unit>>

    fun updateOptionGroup(
        optionGroupCd: String,
        request: OptionGroupUpdateRequest
    ): Single<Result<Unit>>

    fun deleteOptionGroup(optionGroupCd: String): Single<Result<Unit>>

    fun deleteOptionGroups(request: OptionGroupsDeleteRequest): Single<Result<Unit>>

    fun optionGroupOptions(optionGroupCd: String, commonCond: CommonCondition? = null): Single<Result<OptionGroupOptionsResponse>>

    fun optionGroupOptionMappers(optionGroupCd: String): Single<Result<OptionGroupOptionMappersResponse>>

    fun mapToOptions(
        optionGroupCd: String,
        request: OptionsMappedByRequest,
    ): Single<Result<Unit>>

    fun changeOptionGroupOptionMapperPriorities(
        optionGroupCd: String,
        request: OptionGroupOptionMapperPrioritiesChangeRequest,
    ): Single<Result<Unit>>

    fun deleteOptionGroupOptionMappers(
        optionGroupCd: String,
        request: OptionGroupOptionMappersDeleteRequest,
    ): Single<Result<Unit>>

    fun optionGroupMainMenus(
        optionGroupCd: String,
        request: MainMenuSearchRequest,
        commonCond: CommonCondition? = null
    ): Single<Result<OptionGroupMainMenusResponse>>

    fun optionGroupMainMenuMappers(optionGroupCd: String): Single<Result<OptionGroupMainMenuMappersResponse>>

    fun mapToMainMenus(
        optionGroupCd: String,
        request: MainMenusMappedByRequest,
    ): Single<Result<Unit>>

    fun changeOptionGroupMainMenuMapperPriorities(
        optionGroupCd: String,
        request: OptionGroupMainMenuMapperPrioritiesChangeRequest,
    ): Single<Result<Unit>>

    fun deleteOptionGroupMainMenuMappers(
        optionGroupCd: String,
        request: OptionGroupMainMenuMappersDeleteRequest,
    ): Single<Result<Unit>>
}