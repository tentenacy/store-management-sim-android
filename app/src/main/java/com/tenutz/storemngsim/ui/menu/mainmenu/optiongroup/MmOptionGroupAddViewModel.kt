package com.tenutz.storemngsim.ui.menu.mainmenu.optiongroup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.storemngsim.data.datasource.api.dto.common.OptionGroupsMappedByRequest
import com.tenutz.storemngsim.data.datasource.api.dto.menu.MainMenuOptionGroupsResponse
import com.tenutz.storemngsim.data.repository.menu.MenuRepository
import com.tenutz.storemngsim.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MmOptionGroupAddViewModel @Inject constructor(
    private val mainMenuRepository: MenuRepository,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    companion object {
        const val EVENT_NAVIGATE_UP = 1000
    }

    private val query = MutableLiveData("")

    private val _mmOptionGroups = MutableLiveData<MainMenuOptionGroupsResponse>()
    val mmOptionGroups: LiveData<MainMenuOptionGroupsResponse> = _mmOptionGroups

    private val args = MmOptionGroupsFragmentArgs.fromSavedStateHandle(savedStateHandle)

    init {
        mmOptionGroups()
    }

    fun mmOptionGroups(
        mainCateCd: String = "2000",
        middleCateCd: String = "3000",
        searchText: String? = null,
    ) {
        searchText?.let { query.value = it }
        mainMenuRepository.mainMenuOptionGroups(mainCateCd, middleCateCd, args.subCategory.subCategoryCode, args.mainMenu.menuCode, CommonCondition(query = query.value))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        _mmOptionGroups.value = it
                    },
                    onFailure = {
                        Logger.e("$it")
                    }
                )
            }.addTo(compositeDisposable)
    }

    fun mapToOptionGroups(
        mainCateCd: String = "2000",
        middleCateCd: String = "3000",
        request: OptionGroupsMappedByRequest,
        callback: () -> Unit,
    ) {
        mainMenuRepository.mapToOptionGroups(
            mainCateCd,
            middleCateCd,
            args.subCategory.subCategoryCode,
            args.mainMenu.menuCode,
            request
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        callback()
                        viewEvent(Pair(EVENT_NAVIGATE_UP, Unit))
                    },
                    onFailure = {
                        Logger.e("$it")
                    }
                )
            }.addTo(compositeDisposable)
    }
}