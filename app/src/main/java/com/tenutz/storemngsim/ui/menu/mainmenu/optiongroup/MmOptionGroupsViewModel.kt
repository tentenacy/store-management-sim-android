package com.tenutz.storemngsim.ui.menu.mainmenu.optiongroup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.datasource.api.dto.menu.MainMenuMappersResponse
import com.tenutz.storemngsim.data.repository.menu.MenuRepository
import com.tenutz.storemngsim.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MmOptionGroupsViewModel @Inject constructor(
    private val menuRepository: MenuRepository,
) : BaseViewModel() {

    private val _mainMenuMappers = MutableLiveData<MainMenuMappersResponse>()
    val mainMenuMappers: LiveData<MainMenuMappersResponse> = _mainMenuMappers

    fun mmOptionGroups(mainCateCd: String, middleCateCd: String, subCateCd: String, mainMenuCd: String) {
        menuRepository.mainMenuMappers(mainCateCd, middleCateCd, subCateCd, mainMenuCd)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        _mainMenuMappers.value = it
                    },
                    onFailure = {
                        Logger.e("$it")
                    },
                )
            }
    }
}