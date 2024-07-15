package com.tenutz.storemngsim.ui.menu.optionmenu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.esafirm.imagepicker.model.Image
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.datasource.api.dto.option.OptionResponse
import com.tenutz.storemngsim.data.datasource.api.dto.option.OptionUpdateRequest
import com.tenutz.storemngsim.data.repository.option.OptionRepository
import com.tenutz.storemngsim.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class OptionMenuDetailsViewModel @Inject constructor(
    private val optionRepository: OptionRepository,
): BaseViewModel() {

    private val _optionMenu = MutableLiveData<OptionResponse>()
    val optionMenu: LiveData<OptionResponse> = _optionMenu

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

    fun optionMenu(optionCd: String) {
        optionRepository.option(optionCd)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        _optionMenu.value = it
                    },
                    onFailure = {
                        Logger.e("$it")
                    },
                )
            }
    }

    fun updateOptionMenu(optionCd: String, request: OptionUpdateRequest, callback: () -> Unit) {
        optionRepository.updateOption(optionCd, request)
            .flatMap {
                optionRepository.option(optionCd)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        _optionMenu.value = it
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