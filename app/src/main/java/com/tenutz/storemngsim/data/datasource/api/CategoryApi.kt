package com.tenutz.storemngsim.data.datasource.api

import com.tenutz.storemngsim.data.datasource.api.dto.category.*
import io.reactivex.rxjava3.core.Completable
import retrofit2.http.*

import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import java.util.*

interface CategoryApi {


    @GET("/categories/main")
    fun mainCategories(
        @Query("query") query: String? = null,
    ): Single<MainCategoriesResponse>

    @GET("/categories/main/{mainCateCd}")
    fun mainCategory(
        @Path("mainCateCd") mainCateCd: String,
    ): Single<MainCategoryResponse>

    @POST("/categories/main")
    fun createMainCategory(
        @Body request: MainCategoryCreateRequest,
    ): Single<Unit>

    @PUT("/categories/main/{mainCateCd}")
    fun updateMainCategory(
        @Path("mainCateCd") mainCateCd: String,
        @Body request: MainCategoryUpdateRequest,
    ): Single<Unit>

    @DELETE("/categories/main/{mainCateCd}")
    fun deleteMainCategory(
        @Path("mainCateCd") mainCateCd: String,
    ): Single<Unit>

    @HTTP(method = "DELETE", path = "/categories/main", hasBody = true)
    fun deleteMainCategories(
        @Body request: CategoriesDeleteRequest,
    ): Completable

    @POST("/categories/main/priorities")
    fun changeMainCategoryPriorities(
        @Body request: CategoryPrioritiesChangeRequest,
    ): Single<Unit>

    @GET("/categories/main/{mainCateCd}/middle")
    fun middleCategories(
        @Path("mainCateCd") mainCateCd: String,
    ): Single<MiddleCategoriesResponse>

    @GET("/categories/main/{mainCateCd}/middle/{middleCateCd}")
    fun middleCategory(
        @Path("mainCateCd") mainCateCd: String,
        @Path("middleCateCd") middleCateCd: String,
    ): Single<MiddleCategoryResponse>

    @POST("/categories/main/{mainCateCd}/middle")
    fun createMiddleCategory(
        @Path("mainCateCd") mainCateCd: String,
        @Part("image") image: MultipartBody.Part? = null,
        @Part("categoryCode") categoryCode: String,
        @Part("categoryName") categoryName: String,
        @Part("use") use: Boolean,
        @Part("businessNumber") businessNumber: String? = null,
        @Part("representativeName") representativeName: String? = null,
        @Part("tel") tel: String? = null,
        @Part("address") address: String? = null,
        @Part("tid") tid: String? = null,
    ): Single<Unit>

    @PUT("/categories/main/{mainCateCd}/middle/{middleCateCd}")
    fun updateMiddleCategory(
        @Path("mainCateCd") mainCateCd: String,
        @Path("middleCateCd") middleCateCd: String,
        @Part("image") image: MultipartBody.Part? = null,
        @Part("categoryName") categoryName: String,
        @Part("use") use: Boolean,
        @Part("businessNumber") businessNumber: String? = null,
        @Part("representativeName") representativeName: String? = null,
        @Part("tel") tel: String? = null,
        @Part("address") address: String? = null,
        @Part("tid") tid: String? = null,
    ): Single<Unit>

    @DELETE("/categories/main/{mainCateCd}/middle/{middleCateCd}")
    fun deleteMiddleCategory(
        @Path("mainCateCd") mainCateCd: String,
        @Path("middleCateCd") middleCateCd: String,
    ): Single<Unit>

    @DELETE("/categories/main/{mainCateCd}/middle")
    fun deleteMiddleCategories(
        @Path("mainCateCd") mainCateCd: String,
        @Body request: CategoriesDeleteRequest,
    ): Single<Unit>

    @POST("/categories/main/{mainCateCd}/middle/priorities")
    fun changeMiddleCategoryPriorities(
        @Path("mainCateCd") mainCateCd: String,
        @Body request: CategoryPrioritiesChangeRequest,
    ): Single<Unit>

    @GET("/categories/main/{mainCateCd}/middle/{middleCateCd}/sub")
    fun subCategories(
        @Path("mainCateCd") mainCateCd: String,
        @Path("middleCateCd") middleCateCd: String,
    ): Single<SubCategoriesResponse>

    @GET("/categories/main/{mainCateCd}/middle/{middleCateCd}/sub/{subCateCd}")
    fun subCategory(
        @Path("mainCateCd") mainCateCd: String,
        @Path("middleCateCd") middleCateCd: String,
        @Path("subCateCd") subCateCd: String,
    ): Single<SubCategoryResponse>

    @POST("/categories/main/{mainCateCd}/middle/{middleCateCd}/sub")
    fun createSubCategory(
        @Path("mainCateCd") mainCateCd: String,
        @Path("middleCateCd") middleCateCd: String,
        @Body request: SubCategoryCreateRequest,
    ): Single<Unit>

    @PUT("/categories/main/{mainCateCd}/middle/{middleCateCd}/sub/{subCateCd}")
    fun updateSubCategory(
        @Path("mainCateCd") mainCateCd: String,
        @Path("middleCateCd") middleCateCd: String,
        @Path("subCateCd") subCateCd: String,
        @Body request: SubCategoryUpdateRequest,
    ): Single<Unit>

    @DELETE("/categories/main/{mainCateCd}/middle/{middleCateCd}/sub/{subCateCd}")
    fun deleteSubCategory(
        @Path("mainCateCd") mainCateCd: String,
        @Path("middleCateCd") middleCateCd: String,
        @Path("subCateCd") subCateCd: String,
    ): Single<Unit>

    @DELETE("/categories/main/{mainCateCd}/middle/{middleCateCd}/sub")
    fun deleteSubCategories(
        @Path("mainCateCd") mainCateCd: String,
        @Path("middleCateCd") middleCateCd: String,
        @Body request: CategoriesDeleteRequest,
    ): Single<Unit>

    @POST("/categories/main/{mainCateCd}/middle/{middleCateCd}/sub/priorities")
    fun changeSubCategoryPriorities(
        @Path("mainCateCd") mainCateCd: String,
        @Path("middleCateCd") middleCateCd: String,
        @Body request: CategoryPrioritiesChangeRequest,
    ): Single<Unit>

}