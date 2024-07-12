package com.tenutz.storemngsim.data.repository.menu

import com.tenutz.storemngsim.data.datasource.api.dto.common.OptionGroupPrioritiesChangeRequest
import com.tenutz.storemngsim.data.datasource.api.dto.common.OptionGroupsDeleteRequest
import com.tenutz.storemngsim.data.datasource.api.dto.common.OptionGroupsMappedByRequest
import com.tenutz.storemngsim.data.datasource.api.dto.menu.*
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import retrofit2.http.*

interface MenuRepository {

    fun mainMenus(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String
    ): Single<MainMenusResponse>

    fun mainMenu(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        mainMenuCd: String
    ): Single<MainMenuResponse>

    fun createMainMenu(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        request: MainMenuCreateRequest,
    ): Single<Unit>

    fun updateMainMenu(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        mainMenuCd: String,
        request: MainMenuUpdateRequest,
    ): Single<Unit>

    fun deleteMainMenu(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        mainMenuCd: String
    ): Single<Unit>

    fun deleteMainMenus(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        request: MenusDeleteRequest
    ): Single<Unit>

    fun changeMainMenuPriorities(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        request: MenuPrioritiesChangeRequest
    ): Single<Unit>

    fun mainMenuOptionGroups(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        mainMenuCd: String
    ): Single<MainMenuOptionGroupsResponse>

    fun mainMenuMappers(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        mainMenuCd: String
    ): Single<MainMenuMappersResponse>

    fun mapToOptionGroups(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        mainMenuCd: String,
        request: OptionGroupsMappedByRequest
    ): Single<Unit>

    fun deleteMainMenuMappers(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        mainMenuCd: String,
        request: OptionGroupsDeleteRequest,
    ): Single<Unit>

    fun changeMainMenuMapperPriorities(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        mainMenuCd: String,
        request: OptionGroupPrioritiesChangeRequest,
    ): Single<Unit>
}