package com.tenutz.storemngsim.ui.menu.mainmenu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.esafirm.imagepicker.model.Image
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.datasource.api.dto.menu.MainMenuResponse
import com.tenutz.storemngsim.data.datasource.api.dto.menu.MainMenuUpdateRequest
import com.tenutz.storemngsim.data.repository.menu.MenuRepository
import com.tenutz.storemngsim.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainMenuDetailsViewModel @Inject constructor(
    private val menuRepository: MenuRepository,
): BaseViewModel() {

    private val _mainMenu = MutableLiveData<MainMenuResponse>()
    val mainMenu: LiveData<MainMenuResponse> = _mainMenu

    private val _editMode = MutableLiveData<Boolean>(false)
    val editMode: LiveData<Boolean> = _editMode

    private val _image = MutableLiveData<Image?>()
    val image: LiveData<Image?> = _image

    fun setImageUri(image: Image) {
        _image.value = image
    }

    fun switchToEditMode() {
        _editMode.value = true
    }

    fun switchToReadMode() {
        _editMode.value = false
    }

    fun mainMenu(mainCateCd: String, middleCateCd: String, subCateCd: String, mainMenuCd: String) {
        menuRepository.mainMenu(mainCateCd, middleCateCd, subCateCd, mainMenuCd)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        _mainMenu.value = it
                    },
                    onFailure = {
                        Logger.e("$it")
                    },
                )
            }
    }

    fun updateMainMenu(mainCateCd: String, middleCateCd: String, subCateCd: String, mainMenuCd: String, request: MainMenuUpdateRequest, callback: () -> Unit) {
        menuRepository.updateMainMenu(mainCateCd, middleCateCd, subCateCd, mainMenuCd, request)
            .flatMap {
                menuRepository.mainMenu(mainCateCd, middleCateCd, subCateCd, mainMenuCd)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        _mainMenu.value = it
                        callback()
                        switchToReadMode()
                    },
                    onFailure = {
                        Logger.e("$it")
                    },
                )
            }.addTo(compositeDisposable)
    }
}