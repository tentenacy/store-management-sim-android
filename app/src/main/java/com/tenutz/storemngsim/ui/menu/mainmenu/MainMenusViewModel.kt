package com.tenutz.storemngsim.ui.menu.mainmenu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.storemngsim.data.datasource.api.dto.menu.MainMenusResponse
import com.tenutz.storemngsim.data.repository.menu.MenuRepository
import com.tenutz.storemngsim.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainMenusViewModel @Inject constructor(
    private val menuRepository: MenuRepository,
    savedStateHandle: SavedStateHandle,
): BaseViewModel() {

    companion object {

        const val EVENT_HIDE_REMOVAL = 1000
        const val EVENT_SHOW_REMOVAL = 1001
    }

    private val query = MutableLiveData("")

    private val _mainMenus = MutableLiveData<MainMenusResponse>()
    val mainMenus: LiveData<MainMenusResponse> = _mainMenus

    private val _hideRemoval = MutableLiveData(true)
    val hideRemoval: LiveData<Boolean> = _hideRemoval

    private val args = MainMenusFragmentArgs.fromSavedStateHandle(savedStateHandle)

    fun showRemoval() {
        _hideRemoval.value = false
        viewEvent(Pair(EVENT_SHOW_REMOVAL, Unit))
    }

    fun hideRemoval() {
        _hideRemoval.value = true
        viewEvent(Pair(EVENT_HIDE_REMOVAL, Unit))
    }

    init {
        mainMenus()
    }

    fun mainMenus(mainCateCd: String = "2000", middleCateCd: String = "3000", searchText: String? = null, callback: () -> Unit = {}) {

        searchText?.let { query.value = it }

        menuRepository.mainMenus(mainCateCd, middleCateCd, args.subCategory.subCategoryCode, CommonCondition(query = query.value))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        _mainMenus.value = it
                        callback()
                    },
                    onFailure = {
                        Logger.e("$it")
                    },
                )
            }.addTo(compositeDisposable)
    }
}