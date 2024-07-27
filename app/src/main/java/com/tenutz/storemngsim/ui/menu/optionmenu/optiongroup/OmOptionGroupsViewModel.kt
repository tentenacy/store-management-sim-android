package com.tenutz.storemngsim.ui.menu.optionmenu.optiongroup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.datasource.api.dto.option.OptionMappersResponse
import com.tenutz.storemngsim.data.repository.option.OptionRepository
import com.tenutz.storemngsim.di.qualifier.NavigationGraphReference
import com.tenutz.storemngsim.di.qualifier.NavigationGraphs
import com.tenutz.storemngsim.ui.base.BaseViewModel
import com.tenutz.storemngsim.ui.menu.optionmenu.optiongroup.args.OmOptionGroupsNavArgs
import com.tenutz.storemngsim.usecase.PutSharedDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class OmOptionGroupsViewModel @Inject constructor(
    private val optionRepository: OptionRepository,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _optionMenuMappers = MutableLiveData<OptionMappersResponse>()
    val optionMenuMappers: LiveData<OptionMappersResponse> = _optionMenuMappers

    private val _navArgs = MutableLiveData<OmOptionGroupsNavArgs>()
    val navArgs: LiveData<OmOptionGroupsNavArgs> = _navArgs

    private val args = OmOptionGroupsFragmentArgs.fromSavedStateHandle(savedStateHandle)

    init {
        omOptionGroups()
    }

    fun omOptionGroups(callback: () -> Unit = {}) {
        optionRepository.optionMappers(args.optionMenu.optionCode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        _optionMenuMappers.value = it
                        callback()
                    },
                    onFailure = {
                        Logger.e("$it")
                    },
                )
            }.addTo(compositeDisposable)
    }
}