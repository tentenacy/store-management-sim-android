package com.tenutz.storemngsim.data.repository.category

import com.tenutz.storemngsim.data.datasource.api.SCKApi
import com.tenutz.storemngsim.data.datasource.api.dto.category.*
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepositoryImpl @Inject constructor(
    private val sckApi: SCKApi,
) : CategoryRepository {

    override fun mainCategories(): Single<MainCategoriesResponse> =
        sckApi.mainCategories()

    override fun mainCategory(mainCateCd: String): Single<MainCategoryResponse> =
        sckApi.mainCategory(mainCateCd)

    override fun createMainCategory(request: MainCategoryCreateRequest): Single<Unit> =
        sckApi.createMainCategory(request)

    override fun updateMainCategory(
        mainCateCd: String,
        request: MainCategoryUpdateRequest
    ): Single<Unit> =
        sckApi.updateMainCategory(
            mainCateCd,
            request,
        )

    override fun deleteMainCategory(mainCateCd: String): Single<Unit> =
        sckApi.deleteMainCategory(mainCateCd)

    override fun deleteMainCategories(request: CategoriesDeleteRequest): Single<Unit> =
        sckApi.deleteMainCategories(request)

    override fun changeMainCategoryPriorities(request: CategoryPrioritiesChangeRequest): Single<Unit> =
        sckApi.changeMainCategoryPriorities(request)

    override fun middleCategories(mainCateCd: String): Single<MiddleCategoriesResponse> =
        sckApi.middleCategories(mainCateCd)

    override fun middleCategory(
        mainCateCd: String,
        middleCateCd: String
    ): Single<MiddleCategoryResponse> =
        sckApi.middleCategory(
            mainCateCd,
            middleCateCd,
        )

    override fun createMiddleCategory(
        mainCateCd: String,
        request: MiddleCategoryCreateRequest
    ): Single<Unit> =
        sckApi.createMiddleCategory(
            mainCateCd,
            request.image,
            request.categoryCode,
            request.categoryName,
            request.use,
            request.businessNumber,
            request.representativeName,
            request.tel,
            request.address,
            request.tid,
        )

    override fun updateMiddleCategory(
        mainCateCd: String,
        middleCateCd: String,
        request: MiddleCategoryUpdateRequest
    ): Single<Unit> =
        sckApi.updateMiddleCategory(
            mainCateCd,
            middleCateCd,
            request.image,
            request.categoryName,
            request.use,
            request.businessNumber,
            request.representativeName,
            request.tel,
            request.address,
            request.tid,
        )

    override fun deleteMiddleCategory(mainCateCd: String, middleCateCd: String): Single<Unit> =
        sckApi.deleteMiddleCategory(
            mainCateCd,
            middleCateCd,
        )

    override fun deleteMiddleCategories(
        mainCateCd: String,
        request: CategoriesDeleteRequest
    ): Single<Unit> =
        sckApi.deleteMiddleCategories(
            mainCateCd,
            request,
        )

    override fun changeMiddleCategoryPriorities(
        mainCateCd: String,
        request: CategoryPrioritiesChangeRequest
    ): Single<Unit> =
        sckApi.changeMiddleCategoryPriorities(
            mainCateCd,
            request,
        )

    override fun subCategories(
        mainCateCd: String,
        middleCateCd: String
    ): Single<SubCategoriesResponse> =
        sckApi.subCategories(
            mainCateCd,
            middleCateCd,
        )

    override fun subCategory(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String
    ): Single<SubCategoryResponse> =
        sckApi.subCategory(
            mainCateCd,
            middleCateCd,
            subCateCd,
        )

    override fun createSubCategory(
        mainCateCd: String,
        middleCateCd: String,
        request: SubCategoryCreateRequest
    ): Single<Unit> =
        sckApi.createSubCategory(
            mainCateCd,
            middleCateCd,
            request,
        )

    override fun updateSubCategory(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        request: SubCategoryUpdateRequest
    ): Single<Unit> =
        sckApi.updateSubCategory(
            mainCateCd,
            middleCateCd,
            subCateCd,
            request,
        )

    override fun deleteSubCategory(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String
    ): Single<Unit> =
        sckApi.deleteSubCategory(
            mainCateCd,
            middleCateCd,
            subCateCd,
        )

    override fun deleteSubCategories(
        mainCateCd: String,
        middleCateCd: String,
        request: CategoriesDeleteRequest
    ): Single<Unit> =
        sckApi.deleteSubCategories(
            mainCateCd,
            middleCateCd,
            request,
        )

    override fun changeSubCategoryPriorities(
        mainCateCd: String,
        middleCateCd: String,
        request: CategoryPrioritiesChangeRequest
    ): Single<Unit> =
        sckApi.changeSubCategoryPriorities(
            mainCateCd,
            middleCateCd,
            request,
        )
}