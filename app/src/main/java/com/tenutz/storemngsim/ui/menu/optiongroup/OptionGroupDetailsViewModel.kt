package com.tenutz.storemngsim.ui.menu.optiongroup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
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
    savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _optionGroup = MutableLiveData<OptionGroupResponse>()
    val optionGroup: LiveData<OptionGroupResponse> = _optionGroup

    private val _editMode = MutableLiveData<Boolean>(false)
    val editMode: LiveData<Boolean> = _editMode

    private val args = OptionGroupDetailsFragmentArgs.fromSavedStateHandle(savedStateHandle)

    fun switchToEditMode() {
        _editMode.value = true
    }

    fun switchToReadMode() {
        _editMode.value = false
    }

    init {
        optionGroup()
    }

    fun optionGroup(callback: () -> Unit = {}) {
        optionGroupRepository.optionGroup(args.optionGroupCode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        _optionGroup.value = it
                        callback()
                    },
                    onFailure = {
                        Logger.e("$it")
                    },
                )
            }.addTo(compositeDisposable)
    }

    fun updateOptionGroup(request: OptionGroupUpdateRequest, callback: () -> Unit) {
        optionGroupRepository.updateOptionGroup(args.optionGroupCode, request)
            .flatMap {
                optionGroupRepository.optionGroup(args.optionGroupCode)
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