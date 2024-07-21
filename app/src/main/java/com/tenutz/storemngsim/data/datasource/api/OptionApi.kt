package com.tenutz.storemngsim.data.datasource.api

import com.tenutz.storemngsim.data.datasource.api.dto.common.OptionGroupPrioritiesChangeRequest
import com.tenutz.storemngsim.data.datasource.api.dto.common.OptionGroupsDeleteRequest
import com.tenutz.storemngsim.data.datasource.api.dto.common.OptionGroupsMappedByRequest
import com.tenutz.storemngsim.data.datasource.api.dto.option.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import retrofit2.http.*

interface OptionApi {

    @GET("/options")
    fun options(@Query("query") query: String? = null): Single<OptionsResponse>

    @GET("/options/{optionCd}")
    fun option(@Path("optionCd") optionCd: String): Single<OptionResponse>

    @Multipart
    @POST("/options")
    fun createOption(
        @Part image: MultipartBody.Part? = null,
        @Part("optionCode") optionCode: String,
        @Part("optionName") optionName: String,
        @Part("price") price: Int,
        @Part("discountedPrice") discountedPrice: Int? = null,
        @Part("additionalPackagingPrice") additionalPackagingPrice: Int? = null,
        @Part("packaging") packaging: String,
        @Part("outOfStock") outOfStock: Boolean,
        @Part("use") use: Boolean,
        @Part("optionNameKor") optionNameKor: String? = null,
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
    ): Single<Unit>

    @Multipart
    @PUT("/options/{optionCd}")
    fun updateOption(
        @Path("optionCd") optionCd: String,
        @Part image: MultipartBody.Part? = null,
        @Part("optionName") optionName: String,
        @Part("price") price: Int,
        @Part("discountedPrice") discountedPrice: Int? = null,
        @Part("additionalPackagingPrice") additionalPackagingPrice: Int? = null,
        @Part("packaging") packaging: String,
        @Part("outOfStock") outOfStock: Boolean,
        @Part("use") use: Boolean?,
        @Part("optionNameKor") optionNameKor: String? = null,
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
    ): Single<Unit>

    @HTTP(method = "DELETE", path = "/options/{optionCd}", hasBody = true)
    fun deleteOption(@Path("optionCd") optionCd: String): Completable

    @HTTP(method = "DELETE", path = "/options", hasBody = true)
    fun deleteOptions(@Body request: OptionsDeleteRequest): Completable

    @GET("/options/{optionCd}/option-groups")
    fun optionOptionGroups(@Path("optionCd") optionCd: String, @Query("query") query: String? = null): Single<OptionOptionGroupsResponse>

    @GET("/options/{optionCd}/mappers")
    fun optionMappers(@Path("optionCd") optionCd: String): Single<OptionMappersResponse>

    @POST("/options/{optionCd}/mapped-by")
    fun mapToOptionGroups(
        @Path("optionCd") optionCd: String,
        @Body request: OptionGroupsMappedByRequest
    ): Single<Unit>

    @HTTP(method = "DELETE", path = "/options/{optionCd}/mappers", hasBody = true)
    fun deleteOptionMappers(
        @Path("optionCd") optionCd: String,
        @Body request: OptionGroupsDeleteRequest
    ): Completable

    @POST("/options/{optionCd}/mappers/priorities")
    fun changeOptionMapperPriorities(
        @Path("optionCd") optionCd: String,
        @Body request: OptionGroupPrioritiesChangeRequest
    ): Single<Unit>
}