package com.tenutz.storemngsim.ui.menu.optiongroup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.OptionGroupResponse
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.OptionGroupUpdateRequest
import com.tenutz.storemngsim.data.repository.optiongroup.OptionGroupRepository
import com.tenutz.storemngsim.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class OptionGroupDetailsViewModel @Inject constructor(
    private val optionGroupRepository: OptionGroupRepository,
) : BaseViewModel() {

    private val _optionGroup = MutableLiveData<OptionGroupResponse>()
    val optionGroup: LiveData<OptionGroupResponse> = _optionGroup

    private val _editMode = MutableLiveData<Boolean>(false)
    val editMode: LiveData<Boolean> = _editMode

    fun switchToEditMode() {
        _editMode.value = true
    }

    fun switchToReadMode() {
        _editMode.value = false
    }

    fun optionGroup(mainCateCd: String) {
        optionGroupRepository.optionGroup(mainCateCd)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        _optionGroup.value = it
                    },
                    onFailure = {
                        Logger.e("$it")
                    },
                )
            }.addTo(compositeDisposable)
    }

    fun updateOptionGroup(mainCateCd: String, request: OptionGroupUpdateRequest, callback: () -> Unit) {
        optionGroupRepository.updateOptionGroup(mainCateCd, request)
            .flatMap {
                optionGroupRepository.optionGroup(mainCateCd)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        _optionGroup.value = it
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