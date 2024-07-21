package com.tenutz.storemngsim.data.repository.menu

import com.tenutz.storemngsim.data.datasource.api.SMSApi
import com.tenutz.storemngsim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.storemngsim.data.datasource.api.dto.common.OptionGroupPrioritiesChangeRequest
import com.tenutz.storemngsim.data.datasource.api.dto.common.OptionGroupsDeleteRequest
import com.tenutz.storemngsim.data.datasource.api.dto.common.OptionGroupsMappedByRequest
import com.tenutz.storemngsim.data.datasource.api.dto.menu.*
import com.tenutz.storemngsim.utils.constant.RetryPolicyConstant
import com.tenutz.storemngsim.utils.ext.applyRetryPolicy
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class MenuRepositoryImpl @Inject constructor(
    private val SMSApi: SMSApi,
) : MenuRepository {

    override fun mainMenus(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        commonCond: CommonCondition?
    ): Single<Result<MainMenusResponse>> =
        SMSApi.mainMenus(
            mainCateCd,
            middleCateCd,
            subCateCd,
            commonCond?.query
        )
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun mainMenu(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        mainMenuCd: String
    ): Single<Result<MainMenuResponse>> =
        SMSApi.mainMenu(
            mainCateCd,
            middleCateCd,
            subCateCd,
            mainMenuCd,
        )
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun createMainMenu(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        request: MainMenuCreateRequest
    ): Single<Result<Unit>> =
        SMSApi.createMainMenu(
            mainCateCd,
            middleCateCd,
            subCateCd,
            request.image,
            request.menuCode,
            request.menuName,
            request.price,
            request.discountedPrice,
            request.additionalPackagingPrice,
            request.packaging,
            request.outOfStock,
            request.use,
            request.ingredientDisplay,
            request.mainMenuNameKor,
            request.highlightType,
            request.showDateFrom,
            request.showDateTo,
            request.showTimeFrom,
            request.showTimeTo,
            request.showDayOfWeek,
            request.eventDateFrom,
            request.eventDateTo,
            request.eventTimeFrom,
            request.eventTimeTo,
            request.eventDayOfWeek,
            request.memoKor,
            request.ingredientDetails,
        )
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun updateMainMenu(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        mainMenuCd: String,
        request: MainMenuUpdateRequest
    ): Single<Result<Unit>> =
        SMSApi.updateMainMenu(
            mainCateCd,
            middleCateCd,
            subCateCd,
            mainMenuCd,
            request.image,
            request.menuName,
            request.price,
            request.discountedPrice,
            request.additionalPackagingPrice,
            request.packaging,
            request.outOfStock,
            request.use,
            request.ingredientDisplay,
            request.mainMenuNameKor,
            request.highlightType,
            request.showDateFrom,
            request.showDateTo,
            request.showTimeFrom,
            request.showTimeTo,
            request.showDayOfWeek,
            request.eventDateFrom,
            request.eventDateTo,
            request.eventTimeFrom,
            request.eventTimeTo,
            request.eventDayOfWeek,
            request.memoKor,
            request.ingredientDetails,
        )
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun deleteMainMenu(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        mainMenuCd: String
    ): Single<Result<Unit>> =
        SMSApi.deleteMainMenu(
            mainCateCd,
            middleCateCd,
            subCateCd,
            mainMenuCd,
        )
            .toSingle { }
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun deleteMainMenus(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        request: MenusDeleteRequest
    ): Single<Result<Unit>> =
        SMSApi.deleteMainMenus(
            mainCateCd,
            middleCateCd,
            subCateCd,
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

    override fun changeMainMenuPriorities(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        request: MenuPrioritiesChangeRequest
    ): Single<Result<Unit>> =
        SMSApi.changeMainMenuPriorities(
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

    override fun mainMenuOptionGroups(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        mainMenuCd: String,
        commonCond: CommonCondition?
    ): Single<Result<MainMenuOptionGroupsResponse>> =
        SMSApi.mainMenuOptionGroups(
            mainCateCd,
            middleCateCd,
            subCateCd,
            mainMenuCd,
            commonCond?.query
        )
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun mainMenuMappers(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        mainMenuCd: String
    ): Single<Result<MainMenuMappersResponse>> =
        SMSApi.mainMenuMappers(
            mainCateCd,
            middleCateCd,
            subCateCd,
            mainMenuCd,
        )
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun mapToOptionGroups(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        mainMenuCd: String,
        request: OptionGroupsMappedByRequest
    ): Single<Result<Unit>> =
        SMSApi.mapToOptionGroups(
            mainCateCd,
            middleCateCd,
            subCateCd,
            mainMenuCd,
            request,
        )
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                ) { Result.failure(it) })

    override fun deleteMainMenuMappers(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        mainMenuCd: String,
        request: OptionGroupsDeleteRequest
    ): Single<Result<Unit>> =
        SMSApi.deleteMainMenuMappers(
            mainCateCd,
            middleCateCd,
            subCateCd,
            mainMenuCd,
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

    override fun changeMainMenuMapperPriorities(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        mainMenuCd: String,
        request: OptionGroupPrioritiesChangeRequest
    ): Single<Result<Unit>> =
        SMSApi.changeMainMenuMapperPriorities(
            mainCateCd,
            middleCateCd,
            subCateCd,
            mainMenuCd,
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