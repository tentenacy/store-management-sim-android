package com.tenutz.storemngsim.ui.menu.mainmenu.optiongroup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.datasource.api.dto.common.OptionGroupPrioritiesChangeRequest
import com.tenutz.storemngsim.data.datasource.api.dto.common.OptionGroupsDeleteRequest
import com.tenutz.storemngsim.data.datasource.api.dto.menu.MainMenuMappersResponse
import com.tenutz.storemngsim.data.repository.menu.MenuRepository
import com.tenutz.storemngsim.di.qualifier.NavigationGraphReference
import com.tenutz.storemngsim.di.qualifier.NavigationGraphs
import com.tenutz.storemngsim.ui.base.BaseViewModel
import com.tenutz.storemngsim.ui.menu.mainmenu.optiongroup.args.MmOptionGroupsEditArgs
import com.tenutz.storemngsim.usecase.GetSharedDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MmOptionGroupsEditViewModel @Inject constructor(
    private val menuRepository: MenuRepository,
    @NavigationGraphReference(NavigationGraphs.MAIN) private val getSharedDataUseCase: GetSharedDataUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    companion object {
        const val EVENT_NAVIGATE_UP = 1000
    }

    private val _mmOptionGroupsEdit = MutableLiveData<MmOptionGroupsEditArgs>()
    val mmOptionGroupsEdit: LiveData<MmOptionGroupsEditArgs> = _mmOptionGroupsEdit

    private val _checkedItemCount = MutableLiveData(0)
    val checkedItemCount: LiveData<Int> = _checkedItemCount

    private val args = MmOptionGroupsFragmentArgs.fromSavedStateHandle(savedStateHandle)

    init {
        getSharedDataUseCase(MainMenuMappersResponse::class)?.let { args ->
            _mmOptionGroupsEdit.value = MmOptionGroupsEditArgs(
                args.mainMenuMappers.map {
                    MmOptionGroupsEditArgs.MainMenuOptionGroup(
                        it.optionGroupCode,
                        it.optionName,
                        it.toggleSelect,
                        it.required,
                        it.priority,
                    )
                }
            )
        }
    }

    fun updateCheckedItemCount() {
        mmOptionGroupsEdit.value?.let {
            _checkedItemCount.value = it.mmOptionGroupsEdit.count { it.checked }
        }
    }

    fun deleteMainMenuMappers(mainCateCd: String = "2000", middleCateCd: String = "3000", request: OptionGroupsDeleteRequest, callback: () -> Unit) {
        menuRepository.deleteMainMenuMappers(mainCateCd, middleCateCd, args.subCategory.subCategoryCode, args.mainMenu.menuCode, request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        viewEvent(Pair(EVENT_NAVIGATE_UP, Unit))
                        callback()
                    },
                    onFailure = {
                        Logger.e("$it")
                    },
                )
            }
            .addTo(compositeDisposable)
    }

    fun changeMainMenuMapperPriorities(mainCateCd: String = "2000", middleCateCd: String = "3000", request: OptionGroupPrioritiesChangeRequest, callback: () -> Unit) {
        menuRepository.changeMainMenuMapperPriorities(mainCateCd, middleCateCd, args.subCategory.subCategoryCode, args.mainMenu.menuCode, request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        callback()
                    },
                    onFailure = {
                        Logger.e("$it")
                    },
                )
            }.addTo(compositeDisposable)
    }
}