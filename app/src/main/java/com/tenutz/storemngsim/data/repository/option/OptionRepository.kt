package com.tenutz.storemngsim.data.repository.option

import com.tenutz.storemngsim.data.datasource.api.dto.common.OptionGroupPrioritiesChangeRequest
import com.tenutz.storemngsim.data.datasource.api.dto.common.OptionGroupsDeleteRequest
import com.tenutz.storemngsim.data.datasource.api.dto.common.OptionGroupsMappedByRequest
import com.tenutz.storemngsim.data.datasource.api.dto.option.*
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import retrofit2.http.*

interface OptionRepository {

    fun options(): Single<OptionsResponse>

    fun option(optionCd: String): Single<OptionResponse>

    fun createOption(
        request: OptionCreateRequest,
    ): Single<Unit>

    fun updateOption(
        optionCd: String,
        request: OptionUpdateRequest,
    ): Single<Unit>

    fun deleteOption(optionCd: String): Single<Unit>

    fun deleteOptions(request: OptionsDeleteRequest): Single<Unit>

    fun optionOptionGroups(optionCd: String): Single<OptionOptionGroupsResponse>

    fun optionMappers(optionCd: String): Single<OptionMappersResponse>

    fun mapToOptionGroups(
        optionCd: String,
        request: OptionGroupsMappedByRequest
    ): Single<Unit>

    fun deleteOptionMappers(
        optionCd: String,
        request: OptionGroupsDeleteRequest
    ): Single<Unit>

    fun changeOptionMapperPriorities(
        optionCd: String,
        request: OptionGroupPrioritiesChangeRequest
    ): Single<Unit>
}