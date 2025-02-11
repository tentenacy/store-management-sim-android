package com.tenutz.storemngsim.ui.menu.optionmenu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.datasource.api.dto.option.OptionsDeleteRequest
import com.tenutz.storemngsim.data.repository.option.OptionRepository
import com.tenutz.storemngsim.ui.base.BaseViewModel
import com.tenutz.storemngsim.ui.menu.optionmenu.args.OptionMenusEditArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class OptionMenusEditViewModel @Inject constructor(
    private val optionRepository: OptionRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    companion object {
        const val EVENT_NAVIGATE_UP = 1000
    }

    private val _optionMenusEdit = MutableLiveData<OptionMenusEditArgs>()
    val optionMenusEdit: LiveData<OptionMenusEditArgs> = _optionMenusEdit

    private val _checkedItemCount = MutableLiveData(0)
    val checkedItemCount: LiveData<Int> = _checkedItemCount

    private val args = OptionMenusEditFragmentArgs.fromSavedStateHandle(savedStateHandle)

    init {
        _optionMenusEdit.value = OptionMenusEditArgs(
            args.optionMenus.options.map {
                OptionMenusEditArgs.OptionEdit(
                    it.storeCode,
                    it.optionCode,
                    it.optionName,
                    it.imageUrl,
                    it.price,
                    it.use,
                )
            }
        )
    }

    fun updateCheckedItemCount() {
        optionMenusEdit.value?.let {
            _checkedItemCount.value = it.optionMenusEdit.count { it.checked }
        }
    }

    fun deleteOptionMenus(request: OptionsDeleteRequest, callback: () -> Unit) {
        optionRepository.deleteOptions(request)
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