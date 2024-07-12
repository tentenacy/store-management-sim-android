package com.tenutz.storemngsim.data.repository.menu

import com.tenutz.storemngsim.data.datasource.api.SCKApi
import com.tenutz.storemngsim.data.datasource.api.dto.common.OptionGroupPrioritiesChangeRequest
import com.tenutz.storemngsim.data.datasource.api.dto.common.OptionGroupsDeleteRequest
import com.tenutz.storemngsim.data.datasource.api.dto.common.OptionGroupsMappedByRequest
import com.tenutz.storemngsim.data.datasource.api.dto.menu.*
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class MenuRepositoryImpl @Inject constructor(
    private val sckApi: SCKApi,
) : MenuRepository {

    override fun mainMenus(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String
    ): Single<MainMenusResponse> =
        sckApi.mainMenus(
            mainCateCd,
            middleCateCd,
            subCateCd,
        )

    override fun mainMenu(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        mainMenuCd: String
    ): Single<MainMenuResponse> =
        sckApi.mainMenu(
            mainCateCd,
            middleCateCd,
            subCateCd,
            mainMenuCd,
        )

    override fun createMainMenu(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        request: MainMenuCreateRequest
    ): Single<Unit> =
        sckApi.createMainMenu(
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

    override fun updateMainMenu(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        mainMenuCd: String,
        request: MainMenuUpdateRequest
    ): Single<Unit> =
        sckApi.updateMainMenu(
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

    override fun deleteMainMenu(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        mainMenuCd: String
    ): Single<Unit> =
        sckApi.deleteMainMenu(
            mainCateCd,
            middleCateCd,
            subCateCd,
            mainMenuCd,
        )

    override fun deleteMainMenus(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        request: MenusDeleteRequest
    ): Single<Unit> =
        sckApi.deleteMainMenus(
            mainCateCd,
            middleCateCd,
            subCateCd,
            request,
        )

    override fun changeMainMenuPriorities(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        request: MenuPrioritiesChangeRequest
    ): Single<Unit> =
        sckApi.changeMainMenuPriorities(
            mainCateCd,
            middleCateCd,
            subCateCd,
            request,
        )

    override fun mainMenuOptionGroups(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        mainMenuCd: String
    ): Single<MainMenuOptionGroupsResponse> =
        sckApi.mainMenuOptionGroups(
            mainCateCd,
            middleCateCd,
            subCateCd,
            mainMenuCd,
        )

    override fun mainMenuMappers(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        mainMenuCd: String
    ): Single<MainMenuMappersResponse> =
        sckApi.mainMenuMappers(
            mainCateCd,
            middleCateCd,
            subCateCd,
            mainMenuCd,
        )

    override fun mapToOptionGroups(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        mainMenuCd: String,
        request: OptionGroupsMappedByRequest
    ): Single<Unit> =
        sckApi.mapToOptionGroups(
            mainCateCd,
            middleCateCd,
            subCateCd,
            mainMenuCd,
            request,
        )

    override fun deleteMainMenuMappers(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        mainMenuCd: String,
        request: OptionGroupsDeleteRequest
    ): Single<Unit> =
        sckApi.deleteMainMenuMappers(
            mainCateCd,
            middleCateCd,
            subCateCd,
            mainMenuCd,
            request,
        )

    override fun changeMainMenuMapperPriorities(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        mainMenuCd: String,
        request: OptionGroupPrioritiesChangeRequest
    ): Single<Unit> =
        sckApi.changeMainMenuMapperPriorities(
            mainCateCd,
            middleCateCd,
            subCateCd,
            mainMenuCd,
            request,
        )
}