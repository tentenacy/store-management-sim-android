package com.tenutz.storemngsim.data.datasource.api

import com.tenutz.storemngsim.data.datasource.api.dto.common.OptionGroupPrioritiesChangeRequest
import com.tenutz.storemngsim.data.datasource.api.dto.common.OptionGroupsDeleteRequest
import com.tenutz.storemngsim.data.datasource.api.dto.common.OptionGroupsMappedByRequest
import com.tenutz.storemngsim.data.datasource.api.dto.menu.*
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import retrofit2.http.*

interface MenuApi {

    @GET("/main-menus/categories/main/{mainCateCd}/middle/{middleCateCd}/sub/{subCateCd}")
    fun mainMenus(
        @Path("mainCateCd") mainCateCd: String,
        @Path("middleCateCd") middleCateCd: String,
        @Path("subCateCd") subCateCd: String
    ): Single<MainMenusResponse>

    @GET("/main-menus/categories/main/{mainCateCd}/middle/{middleCateCd}/sub/{subCateCd}/{mainMenuCd}")
    fun mainMenu(
        @Path("mainCateCd") mainCateCd: String,
        @Path("middleCateCd") middleCateCd: String,
        @Path("subCateCd") subCateCd: String,
        @Path("mainMenuCd") mainMenuCd: String
    ): Single<MainMenuResponse>

    @POST("/main-menus/categories/main/{mainCateCd}/middle/{middleCateCd}/sub/{subCateCd}")
    fun createMainMenu(
        @Path("mainCateCd") mainCateCd: String,
        @Path("middleCateCd") middleCateCd: String,
        @Path("subCateCd") subCateCd: String,
        @Part("image") image: MultipartBody.Part? = null,
        @Part("menuCode") menuCode: String,
        @Part("menuName") menuName: String,
        @Part("price") price: Int,
        @Part("discountedPrice") discountedPrice: Int?,
        @Part("additionalPackagingPrice") additionalPackagingPrice: Int?,
        @Part("packaging") packaging: String,
        @Part("outOfStock") outOfStock: Boolean,
        @Part("use") use: Boolean,
        @Part("ingredientDisplay") ingredientDisplay: Boolean,
        @Part("mainMenuNameKor") mainMenuNameKor: String? = null,
        @Part("highlightType") highlightType: String,
        @Part("showDateFrom") showDateFrom: String? = null,
        @Part("showDateTo") showDateTo: String? = null,
        @Part("showTimeFrom") showTimeFrom: String? = null,
        @Part("showTimeTo") showTimeTo: String? = null,
        @Part("showDayOfWeek") showDayOfWeek: String? = null,
        @Part("eventDateFrom") eventDateFrom: String? = null,
        @Part("eventDateTo") eventDateTo: String? = null,
        @Part("eventTimeFrom") eventTimeFrom: String? = null,
        @Part("eventTimeTo") eventTimeTo: String? = null,
        @Part("eventDayOfWeek") eventDayOfWeek: String? = null,
        @Part("memoKor") memoKor: String? = null,
        @Part("ingredientDetails") ingredientDetails: String? = null,
    ): Single<Unit>

    @PUT("/main-menus/categories/main/{mainCateCd}/middle/{middleCateCd}/sub/{subCateCd}/{mainMenuCd}")
    fun updateMainMenu(
        @Path("mainCateCd") mainCateCd: String,
        @Path("middleCateCd") middleCateCd: String,
        @Path("subCateCd") subCateCd: String,
        @Path("mainMenuCd") mainMenuCd: String,
        @Part("image") image: MultipartBody.Part? = null,
        @Part("menuName") menuName: String,
        @Part("price") price: Int,
        @Part("discountedPrice") discountedPrice: Int?,
        @Part("additionalPackagingPrice") additionalPackagingPrice: Int?,
        @Part("packaging") packaging: String,
        @Part("outOfStock") outOfStock: Boolean,
        @Part("use") use: Boolean,
        @Part("ingredientDisplay") ingredientDisplay: Boolean,
        @Part("mainMenuNameKor") mainMenuNameKor: String? = null,
        @Part("highlightType") highlightType: String,
        @Part("showDateFrom") showDateFrom: String? = null,
        @Part("showDateTo") showDateTo: String? = null,
        @Part("showTimeFrom") showTimeFrom: String? = null,
        @Part("showTimeTo") showTimeTo: String? = null,
        @Part("showDayOfWeek") showDayOfWeek: String? = null,
        @Part("eventDateFrom") eventDateFrom: String? = null,
        @Part("eventDateTo") eventDateTo: String? = null,
        @Part("eventTimeFrom") eventTimeFrom: String? = null,
        @Part("eventTimeTo") eventTimeTo: String? = null,
        @Part("eventDayOfWeek") eventDayOfWeek: String? = null,
        @Part("memoKor") memoKor: String? = null,
        @Part("ingredientDetails") ingredientDetails: String? = null,
    ): Single<Unit>

    @DELETE("/main-menus/categories/main/{mainCateCd}/middle/{middleCateCd}/sub/{subCateCd}/{mainMenuCd}")
    fun deleteMainMenu(
        @Path("mainCateCd") mainCateCd: String,
        @Path("middleCateCd") middleCateCd: String,
        @Path("subCateCd") subCateCd: String,
        @Path("mainMenuCd") mainMenuCd: String
    ): Single<Unit>

    @DELETE("/main-menus/categories/main/{mainCateCd}/middle/{middleCateCd}/sub/{subCateCd}")
    fun deleteMainMenus(
        @Path("mainCateCd") mainCateCd: String,
        @Path("middleCateCd") middleCateCd: String,
        @Path("subCateCd") subCateCd: String,
        @Body request: MenusDeleteRequest
    ): Single<Unit>

    @POST("/main-menus/categories/main/{mainCateCd}/middle/{middleCateCd}/sub/{subCateCd}/priorities")
    fun changeMainMenuPriorities(
        @Path("mainCateCd") mainCateCd: String,
        @Path("middleCateCd") middleCateCd: String,
        @Path("subCateCd") subCateCd: String,
        @Body request: MenuPrioritiesChangeRequest
    ): Single<Unit>

    @GET("/main-menus/categories/main/{mainCateCd}/middle/{middleCateCd}/sub/{subCateCd}/{mainMenuCd}/option-groups")
    fun mainMenuOptionGroups(
        @Path("mainCateCd") mainCateCd: String,
        @Path("middleCateCd") middleCateCd: String,
        @Path("subCateCd") subCateCd: String,
        @Path("mainMenuCd") mainMenuCd: String
    ): Single<MainMenuOptionGroupsResponse>

    @GET("/main-menus/categories/main/{mainCateCd}/middle/{middleCateCd}/sub/{subCateCd}/{mainMenuCd}/mappers")
    fun mainMenuMappers(
        @Path("mainCateCd") mainCateCd: String,
        @Path("middleCateCd") middleCateCd: String,
        @Path("subCateCd") subCateCd: String,
        @Path("mainMenuCd") mainMenuCd: String
    ): Single<MainMenuMappersResponse>

    @POST("/main-menus/categories/main/{mainCateCd}/middle/{middleCateCd}/sub/{subCateCd}/{mainMenuCd}/mapped-by")
    fun mapToOptionGroups(
        @Path("mainCateCd") mainCateCd: String,
        @Path("middleCateCd") middleCateCd: String,
        @Path("subCateCd") subCateCd: String,
        @Path("mainMenuCd") mainMenuCd: String,
        @Body request: OptionGroupsMappedByRequest
    ): Single<Unit>

    @DELETE("/main-menus/categories/main/{mainCateCd}/middle/{middleCateCd}/sub/{subCateCd}/{mainMenuCd}/mappers")
    fun deleteMainMenuMappers(
        @Path("mainCateCd") mainCateCd: String,
        @Path("middleCateCd") middleCateCd: String,
        @Path("subCateCd") subCateCd: String,
        @Path("mainMenuCd") mainMenuCd: String,
        @Body request: OptionGroupsDeleteRequest,
    ): Single<Unit>

    @POST("/main-menus/categories/main/{mainCateCd}/middle/{middleCateCd}/sub/{subCateCd}/{mainMenuCd}/mappers/priorities")
    fun changeMainMenuMapperPriorities(
        @Path("mainCateCd") mainCateCd: String,
        @Path("middleCateCd") middleCateCd: String,
        @Path("subCateCd") subCateCd: String,
        @Path("mainMenuCd") mainMenuCd: String,
        @Body request: OptionGroupPrioritiesChangeRequest,
    ): Single<Unit>
}