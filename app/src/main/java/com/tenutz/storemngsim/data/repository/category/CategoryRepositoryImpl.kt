package com.tenutz.storemngsim.data.repository.category

import com.tenutz.storemngsim.data.datasource.api.SMSApi
import com.tenutz.storemngsim.data.datasource.api.dto.category.*
import com.tenutz.storemngsim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.storemngsim.utils.constant.RetryPolicyConstant
import com.tenutz.storemngsim.utils.ext.applyRetryPolicy
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepositoryImpl @Inject constructor(
    private val sckApi: SMSApi,
) : CategoryRepository {

    override fun mainCategories(cond: CommonCondition?): Single<Result<MainCategoriesResponse>> =
        sckApi.mainCategories(cond?.query)
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun mainCategory(mainCateCd: String): Single<Result<MainCategoryResponse>> =
        sckApi.mainCategory(mainCateCd)
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun createMainCategory(request: MainCategoryCreateRequest): Single<Result<Unit>> =
        sckApi.createMainCategory(request)
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun updateMainCategory(
        mainCateCd: String,
        request: MainCategoryUpdateRequest
    ): Single<Result<Unit>> =
        sckApi.updateMainCategory(
            mainCateCd,
            request,
        )
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun deleteMainCategory(mainCateCd: String): Single<Result<Unit>> =
        sckApi.deleteMainCategory(mainCateCd)
            .toSingle {}
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun deleteMainCategories(request: CategoriesDeleteRequest): Single<Result<Unit>> =
        sckApi.deleteMainCategories(request)
            .toSingle {}
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun changeMainCategoryPriorities(request: CategoryPrioritiesChangeRequest): Single<Result<Unit>> =
        sckApi.changeMainCategoryPriorities(request)
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun middleCategories(mainCateCd: String): Single<Result<MiddleCategoriesResponse>> =
        sckApi.middleCategories(mainCateCd)
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun middleCategory(
        mainCateCd: String,
        middleCateCd: String
    ): Single<Result<MiddleCategoryResponse>> =
        sckApi.middleCategory(
            mainCateCd,
            middleCateCd,
        )
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun createMiddleCategory(
        mainCateCd: String,
        request: MiddleCategoryCreateRequest
    ): Single<Result<Unit>> =
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
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun updateMiddleCategory(
        mainCateCd: String,
        middleCateCd: String,
        request: MiddleCategoryUpdateRequest
    ): Single<Result<Unit>> =
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
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun deleteMiddleCategory(
        mainCateCd: String,
        middleCateCd: String
    ): Single<Result<Unit>> =
        sckApi.deleteMiddleCategory(
            mainCateCd,
            middleCateCd,
        )
            .toSingle { }
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun deleteMiddleCategories(
        mainCateCd: String,
        request: CategoriesDeleteRequest
    ): Single<Result<Unit>> =
        sckApi.deleteMiddleCategories(
            mainCateCd,
            request,
        )
            .toSingle { }
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun changeMiddleCategoryPriorities(
        mainCateCd: String,
        request: CategoryPrioritiesChangeRequest
    ): Single<Result<Unit>> =
        sckApi.changeMiddleCategoryPriorities(
            mainCateCd,
            request,
        )
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun subCategories(
        mainCateCd: String,
        middleCateCd: String
    ): Single<Result<SubCategoriesResponse>> =
        sckApi.subCategories(
            mainCateCd,
            middleCateCd,
        )
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun subCategory(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String
    ): Single<Result<SubCategoryResponse>> =
        sckApi.subCategory(
            mainCateCd,
            middleCateCd,
            subCateCd,
        )
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun createSubCategory(
        mainCateCd: String,
        middleCateCd: String,
        request: SubCategoryCreateRequest
    ): Single<Result<Unit>> =
        sckApi.createSubCategory(
            mainCateCd,
            middleCateCd,
            request,
        )
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun updateSubCategory(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        request: SubCategoryUpdateRequest
    ): Single<Result<Unit>> =
        sckApi.updateSubCategory(
            mainCateCd,
            middleCateCd,
            subCateCd,
            request,
        )
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun deleteSubCategory(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String
    ): Single<Result<Unit>> =
        sckApi.deleteSubCategory(
            mainCateCd,
            middleCateCd,
            subCateCd,
        )
            .toSingle { }
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun deleteSubCategories(
        mainCateCd: String,
        middleCateCd: String,
        request: CategoriesDeleteRequest
    ): Single<Result<Unit>> =
        sckApi.deleteSubCategories(
            mainCateCd,
            middleCateCd,
            request,
        )
            .toSingle { }
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun changeSubCategoryPriorities(
        mainCateCd: String,
        middleCateCd: String,
        request: CategoryPrioritiesChangeRequest
    ): Single<Result<Unit>> =
        sckApi.changeSubCategoryPriorities(
            mainCateCd,
            middleCateCd,
            request,
        )
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })
}