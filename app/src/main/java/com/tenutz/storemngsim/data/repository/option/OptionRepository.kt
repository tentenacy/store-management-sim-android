package com.tenutz.storemngsim.data.repository.option

import com.tenutz.storemngsim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.storemngsim.data.datasource.api.dto.common.OptionGroupPrioritiesChangeRequest
import com.tenutz.storemngsim.data.datasource.api.dto.common.OptionGroupsDeleteRequest
import com.tenutz.storemngsim.data.datasource.api.dto.common.OptionGroupsMappedByRequest
import com.tenutz.storemngsim.data.datasource.api.dto.option.*
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import retrofit2.http.*

interface OptionRepository {

    fun options(commonCond: CommonCondition? = null): Single<Result<OptionsResponse>>

    fun option(optionCd: String): Single<Result<OptionResponse>>

    fun createOption(
        request: OptionCreateRequest,
    ): Single<Result<Unit>>
    
    fun updateOption(
        optionCd: String,
        request: OptionUpdateRequest,
    ): Single<Result<Unit>>

    fun deleteOption(optionCd: String): Single<Result<Unit>>

    fun deleteOptions(request: OptionsDeleteRequest): Single<Result<Unit>>

    fun optionOptionGroups(optionCd: String): Single<Result<OptionOptionGroupsResponse>>

    fun optionMappers(optionCd: String): Single<Result<OptionMappersResponse>>

    fun mapToOptionGroups(
        optionCd: String,
        request: OptionGroupsMappedByRequest
    ): Single<Result<Unit>>

    fun deleteOptionMappers(
        optionCd: String,
        request: OptionGroupsDeleteRequest
    ): Single<Result<Unit>>

    fun changeOptionMapperPriorities(
        optionCd: String,
        request: OptionGroupPrioritiesChangeRequest
    ): Single<Result<Unit>>
}