package com.tenutz.storemngsim.data.datasource.api

import com.tenutz.storemngsim.data.datasource.api.dto.common.OptionGroupsDeleteRequest
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.*
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface OptionGroupApi {

    @GET("/option-groups")
    fun optionGroups(): Single<OptionGroupsResponse>

    @GET("/option-groups/{optionGroupCd}")
    fun optionGroup(@Path("optionGroupCd") optionGroupCd: String): Single<OptionGroupResponse>

    @POST("/option-groups")
    fun createOptionGroup(@Body request: OptionGroupCreateRequest)

    @PUT("/option-groups/{optionGroupCd}")
    fun updateOptionGroup(
        @Path("optionGroupCd") optionGroupCd: String,
        @Body request: OptionGroupUpdateRequest,
    )

    @DELETE("/option-groups/{optionGroupCd}")
    fun deleteOptionGroup(@Path("optionGroupCd") optionGroupCd: String)

    @DELETE("/option-groups")
    fun deleteOptionGroups(@Body request: OptionGroupsDeleteRequest)

    @GET("/option-groups/{optionGroupCd}/options")
    fun optionGroupOptions(@Path("optionGroupCd") optionGroupCd: String): Single<OptionGroupOptionsResponse>

    @GET("/option-groups/{optionGroupCd}/option-mappers")
    fun optionGroupOptionMappers(@Path("optionGroupCd") optionGroupCd: String): Single<OptionGroupOptionMappersResponse>

    @POST("/option-groups/{optionGroupCd}/mapped-by-option")
    fun mapToOptions(
        @Path("optionGroupCd") optionGroupCd: String,
        @Body request: OptionsMappedByRequest
    )

    @POST("/option-groups/{optionGroupCd}/option-mappers/priorities")
    fun changeOptionGroupOptionMapperPriorities(
        @Path("optionGroupCd") optionGroupCd: String,
        @Body request: OptionGroupOptionMapperPrioritiesChangeRequest,
    )

    @DELETE("/option-groups/{optionGroupCd}/option-mappers")
    fun deleteOptionGroupOptionMappers(
        @Path("optionGroupCd") optionGroupCd: String,
        @Body request: OptionGroupOptionMappersDeleteRequest,
    )

    @GET("/option-groups/{optionGroupCd}/main-menus")
    fun optionGroupMainMenus(
        @Path("optionGroupCd") optionGroupCd: String,
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
    )

    @POST("/option-groups/{optionGroupCd}/main-menu-mappers/priorities")
    fun changeOptionGroupMainMenuMapperPriorities(
        @Path("optionGroupCd") optionGroupCd: String,
        @Body request: OptionGroupMainMenuMapperPrioritiesChangeRequest,
    )

    @DELETE("/option-groups/{optionGroupCd}/main-menu-mappers")
    fun deleteOptionGroupMainMenuMappers(
        @Path("optionGroupCd") optionGroupCd: String,
        @Body request: OptionGroupMainMenuMappersDeleteRequest,
    )
}