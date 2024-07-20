package com.tenutz.storemngsim.data.repository.category

import com.tenutz.storemngsim.data.datasource.api.SMSApi
import com.tenutz.storemngsim.data.datasource.api.dto.category.*
import com.tenutz.storemngsim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.storemngsim.utils.constant.RetryPolicyConstant
import com.tenutz.storemngsim.utils.ext.applyRetryPolicy
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepositoryImpl @Inject constructor(
    private val SMSApi: SMSApi,
) : CategoryRepository {

    override fun mainCategories(cond: CommonCondition?): Single<Result<MainCategoriesResponse>> =
        SMSApi.mainCategories(cond?.query)
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun mainCategory(mainCateCd: String): Single<Result<MainCategoryResponse>> =
        SMSApi.mainCategory(mainCateCd)
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun createMainCategory(request: MainCategoryCreateRequest): Single<Result<Unit>> =
        SMSApi.createMainCategory(request)
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
        SMSApi.updateMainCategory(
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
        SMSApi.deleteMainCategory(mainCateCd)
            .toSingle {}
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun deleteMainCategories(request: CategoriesDeleteRequest): Single<Result<Unit>> =
        SMSApi.deleteMainCategories(request)
            .toSingle {}
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun changeMainCategoryPriorities(request: CategoryPrioritiesChangeRequest): Single<Result<Unit>> =
        SMSApi.changeMainCategoryPriorities(request)
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun middleCategories(mainCateCd: String): Single<Result<MiddleCategoriesResponse>> =
        SMSApi.middleCategories(mainCateCd)
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
        SMSApi.middleCategory(
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
        SMSApi.createMiddleCategory(
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
        SMSApi.updateMiddleCategory(
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
        SMSApi.deleteMiddleCategory(
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
        SMSApi.deleteMiddleCategories(
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
        SMSApi.changeMiddleCategoryPriorities(
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
        SMSApi.subCategories(
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
        SMSApi.subCategory(
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
        SMSApi.createSubCategory(
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
        SMSApi.updateSubCategory(
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
        SMSApi.deleteSubCategory(
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
        SMSApi.deleteSubCategories(
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
        SMSApi.changeSubCategoryPriorities(
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