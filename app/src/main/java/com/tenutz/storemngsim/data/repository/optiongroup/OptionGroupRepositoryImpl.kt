package com.tenutz.storemngsim.data.repository.optiongroup

import com.tenutz.storemngsim.data.datasource.api.SCKApi
import com.tenutz.storemngsim.data.datasource.api.dto.common.MainMenuSearchRequest
import com.tenutz.storemngsim.data.datasource.api.dto.common.OptionGroupsDeleteRequest
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.*
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class OptionGroupRepositoryImpl @Inject constructor(
    private val sckApi: SCKApi,
) : OptionGroupRepository {
    override fun optionGroups(): Single<OptionGroupsResponse> =
        sckApi.optionGroups()

    override fun optionGroup(optionGroupCd: String): Single<OptionGroupResponse> =
        sckApi.optionGroup(optionGroupCd)

    override fun createOptionGroup(request: OptionGroupCreateRequest) =
        sckApi.createOptionGroup(request)

    override fun updateOptionGroup(optionGroupCd: String, request: OptionGroupUpdateRequest) =
        sckApi.updateOptionGroup(
            optionGroupCd,
            request
        )

    override fun deleteOptionGroup(optionGroupCd: String) =
        sckApi.deleteOptionGroup(optionGroupCd)

    override fun deleteOptionGroups(request: OptionGroupsDeleteRequest) =
        sckApi.deleteOptionGroups(request)

    override fun optionGroupOptions(optionGroupCd: String): Single<OptionGroupOptionsResponse> =
        sckApi.optionGroupOptions(optionGroupCd)

    override fun optionGroupOptionMappers(optionGroupCd: String): Single<OptionGroupOptionMappersResponse> =
        sckApi.optionGroupOptionMappers(optionGroupCd)

    override fun mapToOptions(optionGroupCd: String, request: OptionsMappedByRequest) =
        sckApi.mapToOptions(
            optionGroupCd,
            request,
        )

    override fun changeOptionGroupOptionMapperPriorities(
        optionGroupCd: String,
        request: OptionGroupOptionMapperPrioritiesChangeRequest
    ) =
        sckApi.changeOptionGroupOptionMapperPriorities(
            optionGroupCd,
            request,
        )

    override fun deleteOptionGroupOptionMappers(
        optionGroupCd: String,
        request: OptionGroupOptionMappersDeleteRequest
    ) =
        sckApi.deleteOptionGroupOptionMappers(
            optionGroupCd,
            request,
        )

    override fun optionGroupMainMenus(
        optionGroupCd: String,
        request: MainMenuSearchRequest
    ): Single<OptionGroupMainMenusResponse> =
        sckApi.optionGroupMainMenus(
            optionGroupCd,
            request.mainCateCd,
            request.middleCateCd,
            request.subCateCd,
        )

    override fun optionGroupMainMenuMappers(optionGroupCd: String): Single<OptionGroupMainMenuMappersResponse> =
        sckApi.optionGroupMainMenuMappers(optionGroupCd)

    override fun mapToMainMenus(optionGroupCd: String, request: MainMenusMappedByRequest) =
        sckApi.mapToMainMenus(
            optionGroupCd,
            request,
        )

    override fun changeOptionGroupMainMenuMapperPriorities(
        optionGroupCd: String,
        request: OptionGroupMainMenuMapperPrioritiesChangeRequest
    ) =
        sckApi.changeOptionGroupMainMenuMapperPriorities(
            optionGroupCd,
            request,
        )

    override fun deleteOptionGroupMainMenuMappers(
        optionGroupCd: String,
        request: OptionGroupMainMenuMappersDeleteRequest
    ) =
        sckApi.deleteOptionGroupMainMenuMappers(
            optionGroupCd,
            request,
        )
}