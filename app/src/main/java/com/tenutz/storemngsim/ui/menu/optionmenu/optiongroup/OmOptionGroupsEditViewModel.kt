package com.tenutz.storemngsim.ui.menu.optionmenu.optiongroup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.datasource.api.dto.common.OptionGroupPrioritiesChangeRequest
import com.tenutz.storemngsim.data.datasource.api.dto.common.OptionGroupsDeleteRequest
import com.tenutz.storemngsim.data.datasource.api.dto.option.OptionMappersResponse
import com.tenutz.storemngsim.data.repository.option.OptionRepository
import com.tenutz.storemngsim.di.qualifier.NavigationGraphReference
import com.tenutz.storemngsim.di.qualifier.NavigationGraphs
import com.tenutz.storemngsim.ui.base.BaseViewModel
import com.tenutz.storemngsim.ui.menu.optionmenu.optiongroup.args.OmOptionGroupsEditArgs
import com.tenutz.storemngsim.usecase.GetSharedDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class OmOptionGroupsEditViewModel @Inject constructor(
    private val optionRepository: OptionRepository,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    companion object {
        const val EVENT_NAVIGATE_UP = 1000
    }

    private val _omOptionGroupsEdit = MutableLiveData<OmOptionGroupsEditArgs>()
    val omOptionGroupsEdit: LiveData<OmOptionGroupsEditArgs> = _omOptionGroupsEdit

    private val _checkedItemCount = MutableLiveData(0)
    val checkedItemCount: LiveData<Int> = _checkedItemCount

    val args = OmOptionGroupsEditFragmentArgs.fromSavedStateHandle(savedStateHandle)

    init {
        _omOptionGroupsEdit.value = OmOptionGroupsEditArgs(
            args.optionMappers.optionMappers.map {
                OmOptionGroupsEditArgs.OptionOptionGroupEdit(
                    it.optionGroupCode,
                    it.optionName,
                    it.toggleSelect,
                    it.required,
                    it.priority,
                )
            }
        )
    }

    fun updateCheckedItemCount() {
        omOptionGroupsEdit.value?.let {
            _checkedItemCount.value = it.omOptionGroupsEdit.count { it.checked }
        }
    }

    fun deleteOptionMenuMappers(request: OptionGroupsDeleteRequest, callback: () -> Unit) {
        optionRepository.deleteOptionMappers(args.optionMenu.optionCode, request)
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

    fun changeOptionMenuMapperPriorities(request: OptionGroupPrioritiesChangeRequest, callback: () -> Unit) {
        optionRepository.changeOptionMapperPriorities(args.optionMenu.optionCode, request)
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