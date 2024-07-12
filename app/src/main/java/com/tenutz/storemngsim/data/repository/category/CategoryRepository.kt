package com.tenutz.storemngsim.data.repository.category

import com.tenutz.storemngsim.data.datasource.api.dto.category.*
import io.reactivex.rxjava3.core.Single

interface CategoryRepository {

    fun mainCategories(): Single<MainCategoriesResponse>

    fun mainCategory(
        mainCateCd: String,
    ): Single<MainCategoryResponse>

    fun createMainCategory(
        request: MainCategoryCreateRequest,
    ): Single<Unit>

    fun updateMainCategory(
        mainCateCd: String,
        request: MainCategoryUpdateRequest,
    ): Single<Unit>

    fun deleteMainCategory(
        mainCateCd: String,
    ): Single<Unit>

    fun deleteMainCategories(
        request: CategoriesDeleteRequest,
    ): Single<Unit>

    fun changeMainCategoryPriorities(
        request: CategoryPrioritiesChangeRequest,
    ): Single<Unit>

    fun middleCategories(
        mainCateCd: String,
    ): Single<MiddleCategoriesResponse>

    fun middleCategory(
        mainCateCd: String,
        middleCateCd: String,
    ): Single<MiddleCategoryResponse>

    fun createMiddleCategory(
        mainCateCd: String,
        request: MiddleCategoryCreateRequest,
    ): Single<Unit>

    fun updateMiddleCategory(
        mainCateCd: String,
        middleCateCd: String,
        request: MiddleCategoryUpdateRequest,
    ): Single<Unit>

    fun deleteMiddleCategory(
        mainCateCd: String,
        middleCateCd: String,
    ): Single<Unit>

    fun deleteMiddleCategories(
        mainCateCd: String,
        request: CategoriesDeleteRequest,
    ): Single<Unit>

    fun changeMiddleCategoryPriorities(
        mainCateCd: String,
        request: CategoryPrioritiesChangeRequest,
    ): Single<Unit>

    fun subCategories(
        mainCateCd: String,
        middleCateCd: String,
    ): Single<SubCategoriesResponse>

    fun subCategory(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
    ): Single<SubCategoryResponse>

    fun createSubCategory(
        mainCateCd: String,
        middleCateCd: String,
        request: SubCategoryCreateRequest,
    ): Single<Unit>

    fun updateSubCategory(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        request: SubCategoryUpdateRequest,
    ): Single<Unit>

    fun deleteSubCategory(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
    ): Single<Unit>

    fun deleteSubCategories(
        mainCateCd: String,
        middleCateCd: String,
        request: CategoriesDeleteRequest,
    ): Single<Unit>

    fun changeSubCategoryPriorities(
        mainCateCd: String,
        middleCateCd: String,
        request: CategoryPrioritiesChangeRequest,
    ): Single<Unit>
}