package com.tenutz.storemngsim.data.repository.optiongroup

import com.tenutz.storemngsim.data.datasource.api.dto.common.MainMenuSearchRequest
import com.tenutz.storemngsim.data.datasource.api.dto.common.OptionGroupsDeleteRequest
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.*
import io.reactivex.rxjava3.core.Single

interface OptionGroupRepository {

    fun optionGroups(): Single<OptionGroupsResponse>

    fun optionGroup(optionGroupCd: String): Single<OptionGroupResponse>

    fun createOptionGroup(request: OptionGroupCreateRequest)

    fun updateOptionGroup(
        optionGroupCd: String,
        request: OptionGroupUpdateRequest
    )

    fun deleteOptionGroup(optionGroupCd: String)

    fun deleteOptionGroups(request: OptionGroupsDeleteRequest)

    fun optionGroupOptions(optionGroupCd: String): Single<OptionGroupOptionsResponse>

    fun optionGroupOptionMappers(optionGroupCd: String): Single<OptionGroupOptionMappersResponse>

    fun mapToOptions(
        optionGroupCd: String,
        request: OptionsMappedByRequest,
    )

    fun changeOptionGroupOptionMapperPriorities(
        optionGroupCd: String,
        request: OptionGroupOptionMapperPrioritiesChangeRequest,
    )

    fun deleteOptionGroupOptionMappers(
        optionGroupCd: String,
        request: OptionGroupOptionMappersDeleteRequest,
    )

    fun optionGroupMainMenus(
        optionGroupCd: String,
        request: MainMenuSearchRequest,
    ): Single<OptionGroupMainMenusResponse>

    fun optionGroupMainMenuMappers(optionGroupCd: String): Single<OptionGroupMainMenuMappersResponse>

    fun mapToMainMenus(
        optionGroupCd: String,
        request: MainMenusMappedByRequest,
    )

    fun changeOptionGroupMainMenuMapperPriorities(
        optionGroupCd: String,
        request: OptionGroupMainMenuMapperPrioritiesChangeRequest,
    )

    fun deleteOptionGroupMainMenuMappers(
        optionGroupCd: String,
        request: OptionGroupMainMenuMappersDeleteRequest,
    )
}