package com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.optionmenu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.OptionGroupOptionMapperPrioritiesChangeRequest
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.OptionGroupOptionMappersDeleteRequest
import com.tenutz.storemngsim.data.repository.optiongroup.OptionGroupRepository
import com.tenutz.storemngsim.ui.base.BaseViewModel
import com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.OgMappingMenusFragmentArgs
import com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.mainmenu.args.OgOptionMenusEditArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class OgOptionMenusEditViewModel @Inject constructor(
    private val optionGroupRepository: OptionGroupRepository,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    companion object {
        const val EVENT_NAVIGATE_UP = 1000
    }

    private val _ogOptionMenusEdit = MutableLiveData<OgOptionMenusEditArgs>()
    val ogOptionMenusEdit: LiveData<OgOptionMenusEditArgs> = _ogOptionMenusEdit

    private val _checkedItemCount = MutableLiveData(0)
    val checkedItemCount: LiveData<Int> = _checkedItemCount

    private val ogMappingMenusArgs = OgMappingMenusFragmentArgs.fromSavedStateHandle(savedStateHandle)
    private val args = OgOptionMenusEditFragmentArgs.fromSavedStateHandle(savedStateHandle)


    init {
        _ogOptionMenusEdit.value = OgOptionMenusEditArgs(
            args.ogOptionMenuMappers.optionGroupOptionMappers.map {
                OgOptionMenusEditArgs.OptionGroupOptionMenuMapper(
                    it.storeCode,
                    it.optionCode,
                    it.optionName,
                    it.imageUrl,
                    it.price,
                    it.use,
                    it.priority,
                )
            }
        )
    }

    fun updateCheckedItemCount() {
        ogOptionMenusEdit.value?.let {
            _checkedItemCount.value = it.ogOptionMenuMappersEdit.count { it.checked }
        }
    }

    fun deleteOgOptionMenus(request: OptionGroupOptionMappersDeleteRequest, callback: () -> Unit) {
        optionGroupRepository.deleteOptionGroupOptionMappers(ogMappingMenusArgs.optionGroup.optionGroupCode, request)
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

    fun changeMiddleCategoryPriorities(request: OptionGroupOptionMapperPrioritiesChangeRequest, callback: () -> Unit) {
        optionGroupRepository.changeOptionGroupOptionMapperPriorities(ogMappingMenusArgs.optionGroup.optionGroupCode, request)
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