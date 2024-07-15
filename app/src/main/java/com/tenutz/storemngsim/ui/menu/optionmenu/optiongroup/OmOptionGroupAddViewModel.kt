package com.tenutz.storemngsim.ui.menu.optionmenu.optiongroup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.datasource.api.dto.common.OptionGroupsMappedByRequest
import com.tenutz.storemngsim.data.datasource.api.dto.option.OptionOptionGroupsResponse
import com.tenutz.storemngsim.data.repository.option.OptionRepository
import com.tenutz.storemngsim.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class OmOptionGroupAddViewModel @Inject constructor(
    private val optionRepository: OptionRepository,
) : BaseViewModel() {

    companion object {
        const val EVENT_NAVIGATE_UP = 1000
    }

    private val _omOptionGroups = MutableLiveData<OptionOptionGroupsResponse>()
    val omOptionGroups: LiveData<OptionOptionGroupsResponse> = _omOptionGroups

    fun omOptionGroups(
        optionCd: String,
    ) {
        optionRepository.optionOptionGroups(optionCd)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        _omOptionGroups.value = it
                    },
                    onFailure = {
                        Logger.e("$it")
                    }
                )
            }.addTo(compositeDisposable)
    }

    fun mapToOptionGroups(
        optionCd: String,
        request: OptionGroupsMappedByRequest,
        callback: () -> Unit,
    ) {
        optionRepository.mapToOptionGroups(
            optionCd,
            request
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        callback()
                        viewEvent(Pair(EVENT_NAVIGATE_UP, Unit))
                    },
                    onFailure = {
                        Logger.e("$it")
                    }
                )
            }.addTo(compositeDisposable)
    }
}