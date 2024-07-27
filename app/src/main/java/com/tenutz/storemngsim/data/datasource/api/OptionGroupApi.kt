package com.tenutz.storemngsim.data.datasource.api

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
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface OptionGroupApi {

    @GET("/option-groups")
    fun optionGroups(@Query("query") query: String? = null): Single<OptionGroupsResponse>

    @GET("/option-groups/{optionGroupCd}")
    fun optionGroup(@Path("optionGroupCd") optionGroupCd: String): Single<OptionGroupResponse>

    @POST("/option-groups")
    fun createOptionGroup(@Body request: OptionGroupCreateRequest): Single<Unit>

    @PUT("/option-groups/{optionGroupCd}")
    fun updateOptionGroup(
        @Path("optionGroupCd") optionGroupCd: String,
        @Body request: OptionGroupUpdateRequest,
    ): Single<Unit>

    @HTTP(method = "DELETE", path = "/option-groups/{optionGroupCd}", hasBody = true)
    fun deleteOptionGroup(@Path("optionGroupCd") optionGroupCd: String): Completable

    @HTTP(method = "DELETE", path = "/option-groups", hasBody = true)
    fun deleteOptionGroups(@Body request: OptionGroupsDeleteRequest): Completable

    @GET("/option-groups/{optionGroupCd}/options")
    fun optionGroupOptions(@Path("optionGroupCd") optionGroupCd: String, @Query("query") query: String? = null): Single<OptionGroupOptionsResponse>

    @GET("/option-groups/{optionGroupCd}/option-mappers")
    fun optionGroupOptionMappers(@Path("optionGroupCd") optionGroupCd: String): Single<OptionGroupOptionMappersResponse>

    @POST("/option-groups/{optionGroupCd}/mapped-by-option")
    fun mapToOptions(
        @Path("optionGroupCd") optionGroupCd: String,
        @Body request: OptionsMappedByRequest
    ): Single<Unit>

    @POST("/option-groups/{optionGroupCd}/option-mappers/priorities")
    fun changeOptionGroupOptionMapperPriorities(
        @Path("optionGroupCd") optionGroupCd: String,
        @Body request: OptionGroupOptionMapperPrioritiesChangeRequest,
    ): Single<Unit>

    @HTTP(method = "DELETE", path = "/option-groups/{optionGroupCd}/option-mappers", hasBody = true)
    fun deleteOptionGroupOptionMappers(
        @Path("optionGroupCd") optionGroupCd: String,
        @Body request: OptionGroupOptionMappersDeleteRequest,
    ): Completable

    @GET("/option-groups/{optionGroupCd}/main-menus")
    fun optionGroupMainMenus(
        @Path("optionGroupCd") optionGroupCd: String,
        @Query("query") query: String? = null,
        @Query("mainCateCd") mainCateCd: String,
        @Query("middleCateCd") middleCateCd: String,
        @Query("subCateCd") subCateCd: String,
    ): Single<OptionGroupMainMenusResponse>

    @GET("/option-groups/{optionGroupCd}/main-menu-mappers")
    fun optionGroupMainMenuMappers(@Path("optionGroupCd") optionGroupCd: String): Single<OptionGroupMainMenuMappersResponse>

    @POST("/option-groups/{optionGroupCd}/mapped-by-main-menus")
    fun mapToMainMenus(
        @Path("optionGroupCd") optionGroupCd: String,
        @Body request: MainMenusMappedByRequest,
    ): Single<Unit>

    @POST("/option-groups/{optionGroupCd}/main-menu-mappers/priorities")
    fun changeOptionGroupMainMenuMapperPriorities(
        @Path("optionGroupCd") optionGroupCd: String,
        @Body request: OptionGroupMainMenuMapperPrioritiesChangeRequest,
    ): Single<Unit>

    @HTTP(method = "DELETE", path = "/option-groups/{optionGroupCd}/main-menu-mappers", hasBody = true)
    fun deleteOptionGroupMainMenuMappers(
        @Path("optionGroupCd") optionGroupCd: String,
        @Body request: OptionGroupMainMenuMappersDeleteRequest,
    ): Completable
}