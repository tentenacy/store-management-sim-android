package com.tenutz.storemngsim.data.repository.option

import com.tenutz.storemngsim.data.datasource.api.SCKApi
import com.tenutz.storemngsim.data.datasource.api.dto.common.OptionGroupPrioritiesChangeRequest
import com.tenutz.storemngsim.data.datasource.api.dto.common.OptionGroupsDeleteRequest
import com.tenutz.storemngsim.data.datasource.api.dto.common.OptionGroupsMappedByRequest
import com.tenutz.storemngsim.data.datasource.api.dto.option.*
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class OptionRepositoryImpl @Inject constructor(
    private val sckApi: SCKApi,
) : OptionRepository {
    override fun options(): Single<OptionsResponse> =
        sckApi.options()

    override fun option(optionCd: String): Single<OptionResponse> =
        sckApi.option(optionCd)

    override fun createOption(request: OptionCreateRequest): Single<Unit> =
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

    override fun updateOption(optionCd: String, request: OptionUpdateRequest): Single<Unit> =
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

    override fun deleteOption(optionCd: String): Single<Unit> =
        sckApi.deleteOption(optionCd)

    override fun deleteOptions(request: OptionsDeleteRequest): Single<Unit> =
        sckApi.deleteOptions(request)

    override fun optionOptionGroups(optionCd: String): Single<OptionOptionGroupsResponse> =
        sckApi.optionOptionGroups(optionCd)

    override fun optionMappers(optionCd: String): Single<OptionMappersResponse> =
        sckApi.optionMappers(optionCd)

    override fun mapToOptionGroups(
        optionCd: String,
        request: OptionGroupsMappedByRequest
    ): Single<Unit> =
        sckApi.mapToOptionGroups(
            optionCd,
            request,
        )

    override fun deleteOptionMappers(
        optionCd: String,
        request: OptionGroupsDeleteRequest
    ): Single<Unit> =
        sckApi.deleteOptionMappers(
            optionCd,
            request,
        )

    override fun changeOptionMapperPriorities(
        optionCd: String,
        request: OptionGroupPrioritiesChangeRequest
    ): Single<Unit> =
        sckApi.changeOptionMapperPriorities(
            optionCd,
            request,
        )
}