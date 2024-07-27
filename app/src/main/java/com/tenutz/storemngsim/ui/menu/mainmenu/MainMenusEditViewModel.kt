package com.tenutz.storemngsim.ui.menu.mainmenu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.datasource.api.dto.menu.MenuPrioritiesChangeRequest
import com.tenutz.storemngsim.data.datasource.api.dto.menu.MenusDeleteRequest
import com.tenutz.storemngsim.data.repository.menu.MenuRepository
import com.tenutz.storemngsim.ui.base.BaseViewModel
import com.tenutz.storemngsim.ui.menu.mainmenu.args.MainMenusEditArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainMenusEditViewModel @Inject constructor(
    private val mainMenuRepository: MenuRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    companion object {
        const val EVENT_NAVIGATE_UP = 1000
    }

    private val _mainMenusEdit = MutableLiveData<MainMenusEditArgs>()
    val mainMenusEdit: LiveData<MainMenusEditArgs> = _mainMenusEdit

    private val _checkedItemCount = MutableLiveData(0)
    val checkedItemCount: LiveData<Int> = _checkedItemCount

    private val args = MainMenusEditFragmentArgs.fromSavedStateHandle(savedStateHandle)

    init {
        _mainMenusEdit.value = MainMenusEditArgs(
            args.mainMenus.mainMenus.map {
                MainMenusEditArgs.MainMenuEdit(
                    it.storeCode,
                    it.mainCategoryCode,
                    it.middleCategoryCode,
                    it.subCategoryCode,
                    it.menuCode,
                    it.menuName,
                    it.imageUrl,
                    it.outOfStock,
                    it.price,
                    it.discountingPrice,
                    it.discountedPrice,
                    it.use,
                    it.subCategoryName,
                )
            }
        )
    }

    fun updateCheckedItemCount() {
        mainMenusEdit.value?.let {
            _checkedItemCount.value = it.mainMenusEdit.count { it.checked }
        }
    }

    fun deleteMainMenus(mainCateCd: String = "2000", middleCateCd: String = "3000", request: MenusDeleteRequest, callback: () -> Unit) {
        mainMenuRepository.deleteMainMenus(mainCateCd, middleCateCd, args.subCategory.subCategoryCode, request)
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

    fun changeMainMenuPriorities(mainCateCd: String = "2000", middleCateCd: String = "3000", request: MenuPrioritiesChangeRequest, callback: () -> Unit) {
        mainMenuRepository.changeMainMenuPriorities(mainCateCd, middleCateCd, args.subCategory.subCategoryCode, request)
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