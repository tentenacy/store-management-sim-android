package com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.optionmenu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.OptionGroupOptionMappersResponse
import com.tenutz.storemngsim.data.repository.optiongroup.OptionGroupRepository
import com.tenutz.storemngsim.di.qualifier.NavigationGraphReference
import com.tenutz.storemngsim.di.qualifier.NavigationGraphs
import com.tenutz.storemngsim.ui.base.BaseViewModel
import com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.OgMappingMenusFragmentArgs
import com.tenutz.storemngsim.usecase.GetSharedDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class OgOptionMenusViewModel @Inject constructor(
    private val optionGroupRepository: OptionGroupRepository,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    companion object {

        const val EVENT_HIDE_REMOVAL = 1000
        const val EVENT_SHOW_REMOVAL = 1001
    }

    private val _ogOptionMenuMappers = MutableLiveData<OptionGroupOptionMappersResponse>()
    val ogOptionMenuMappers: LiveData<OptionGroupOptionMappersResponse> = _ogOptionMenuMappers

    private val _hideRemoval = MutableLiveData(true)
    val hideRemoval: LiveData<Boolean> = _hideRemoval

    private val args: OgMappingMenusFragmentArgs = OgMappingMenusFragmentArgs.fromSavedStateHandle(savedStateHandle)

    init {
        ogOptionMenuMappers()
    }

    fun showRemoval() {
        _hideRemoval.value = false
        viewEvent(Pair(EVENT_SHOW_REMOVAL, Unit))
    }

    fun hideRemoval() {
        _hideRemoval.value = true
        viewEvent(Pair(EVENT_HIDE_REMOVAL, Unit))
    }

    fun ogOptionMenuMappers() {
        optionGroupRepository.optionGroupOptionMappers(args.optionGroup.optionGroupCode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        _ogOptionMenuMappers.value = it
                    },
                    onFailure = {
                        Logger.e("$it")
                    },
                )
            }.addTo(compositeDisposable)
    }
}