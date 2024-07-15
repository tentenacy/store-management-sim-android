package com.tenutz.storemngsim.ui.menu.mainmenu.optiongroup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
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
) : BaseViewModel() {

    companion object {
        const val EVENT_NAVIGATE_UP = 1000
    }

    private val _mmOptionGroups = MutableLiveData<MainMenuOptionGroupsResponse>()
    val mmOptionGroups: LiveData<MainMenuOptionGroupsResponse> = _mmOptionGroups

    fun mmOptionGroups(
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        mainMenuCd: String,
    ) {
        mainMenuRepository.mainMenuOptionGroups(mainCateCd, middleCateCd, subCateCd, mainMenuCd)
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
        mainCateCd: String,
        middleCateCd: String,
        subCateCd: String,
        mainMenuCd: String,
        request: OptionGroupsMappedByRequest,
        callback: () -> Unit,
    ) {
        mainMenuRepository.mapToOptionGroups(
            mainCateCd,
            middleCateCd,
            subCateCd,
            mainMenuCd,
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