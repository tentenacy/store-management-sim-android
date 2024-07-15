package com.tenutz.storemngsim.ui.menu.mainmenu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
): BaseViewModel() {

    companion object {

        const val EVENT_HIDE_REMOVAL = 1000
        const val EVENT_SHOW_REMOVAL = 1001
    }

    private val query = MutableLiveData("")

    private val _mainMenus = MutableLiveData<MainMenusResponse>()
    val mainMenus: LiveData<MainMenusResponse> = _mainMenus

    private val _hideRemoval = MutableLiveData(false)
    val hideRemoval: LiveData<Boolean> = _hideRemoval

    fun showRemoval() {
        _hideRemoval.value = false
        viewEvent(Pair(EVENT_SHOW_REMOVAL, Unit))
    }

    fun hideRemoval() {
        _hideRemoval.value = true
        viewEvent(Pair(EVENT_HIDE_REMOVAL, Unit))
    }

    fun mainMenus(mainCateCd: String, middleCateCd: String, subCateCd: String, searchText: String? = null) {

        searchText?.let { query.value = it }

        menuRepository.mainMenus(mainCateCd, middleCateCd, subCateCd, CommonCondition(query = query.value))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        _mainMenus.value = it
                    },
                    onFailure = {
                        Logger.e("$it")
                    },
                )
            }.addTo(compositeDisposable)
    }
}