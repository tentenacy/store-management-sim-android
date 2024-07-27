package com.tenutz.storemngsim.ui.menu.mainmenu.optiongroup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.datasource.api.dto.menu.MainMenuMappersResponse
import com.tenutz.storemngsim.data.repository.menu.MenuRepository
import com.tenutz.storemngsim.di.qualifier.NavigationGraphReference
import com.tenutz.storemngsim.di.qualifier.NavigationGraphs
import com.tenutz.storemngsim.ui.base.BaseViewModel
import com.tenutz.storemngsim.usecase.PutSharedDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MmOptionGroupsViewModel @Inject constructor(
    private val menuRepository: MenuRepository,
    @NavigationGraphReference(NavigationGraphs.MAIN) private val putSharedDataUseCase: PutSharedDataUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _mainMenuMappers = MutableLiveData<MainMenuMappersResponse>()
    val mainMenuMappers: LiveData<MainMenuMappersResponse> = _mainMenuMappers

    private val args = MmOptionGroupsFragmentArgs.fromSavedStateHandle(savedStateHandle)

    init {
        mmOptionGroups()
    }

    fun mmOptionGroups(mainCateCd: String = "2000", middleCateCd: String = "3000", callback: () -> Unit = {}) {
        menuRepository.mainMenuMappers(mainCateCd, middleCateCd, args.subCategory.subCategoryCode, args.mainMenu.menuCode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        _mainMenuMappers.value = it
                        putSharedDataUseCase(it)
                        callback()
                    },
                    onFailure = {
                        Logger.e("$it")
                    },
                )
            }.addTo(compositeDisposable)
    }
}