package com.tenutz.storemngsim.ui.menu.optionmenu.optiongroup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.datasource.api.dto.option.OptionMappersResponse
import com.tenutz.storemngsim.data.repository.option.OptionRepository
import com.tenutz.storemngsim.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class OmOptionGroupsViewModel @Inject constructor(
    private val optionRepository: OptionRepository,
) : BaseViewModel() {

    private val _optionMenuMappers = MutableLiveData<OptionMappersResponse>()
    val optionMenuMappers: LiveData<OptionMappersResponse> = _optionMenuMappers

    fun omOptionGroups(optionCd: String) {
        optionRepository.optionMappers(optionCd)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        _optionMenuMappers.value = it
                    },
                    onFailure = {
                        Logger.e("$it")
                    },
                )
            }
    }
}