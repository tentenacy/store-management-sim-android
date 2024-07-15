package com.tenutz.storemngsim.ui.menu.optiongroup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.datasource.api.dto.common.OptionGroupsDeleteRequest
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.OptionGroupsResponse
import com.tenutz.storemngsim.data.repository.optiongroup.OptionGroupRepository
import com.tenutz.storemngsim.ui.base.BaseViewModel
import com.tenutz.storemngsim.ui.menu.optiongroup.args.OptionGroupsEditArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class OptionGroupsEditViewModel @Inject constructor(
    private val optionGroupRepository: OptionGroupRepository,
) : BaseViewModel() {

    companion object {
        const val EVENT_NAVIGATE_UP = 1000
    }

    private val _optionGroupsEdit = MutableLiveData<OptionGroupsEditArgs>()
    val optionGroupsEdit: LiveData<OptionGroupsEditArgs> = _optionGroupsEdit

    private val _checkedItemCount = MutableLiveData(0)
    val checkedItemCount: LiveData<Int> = _checkedItemCount

    fun updateCheckedItemCount() {
        optionGroupsEdit.value?.let {
            _checkedItemCount.value = it.optionGroupsEdit.count { it.checked }
        }
    }

    fun setOptionGroupsEdit(args: OptionGroupsResponse) {
        _optionGroupsEdit.value = OptionGroupsEditArgs(
            args.optionGroups.map {
                OptionGroupsEditArgs.OptionGroup(
                    it.optionGroupCode,
                    it.optionGroupName,
                    it.toggleSelect,
                    it.required,
                )
            }
        )
    }

    fun deleteOptionGroups(request: OptionGroupsDeleteRequest, callback: () -> Unit) {
        optionGroupRepository.deleteOptionGroups(request)
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
}