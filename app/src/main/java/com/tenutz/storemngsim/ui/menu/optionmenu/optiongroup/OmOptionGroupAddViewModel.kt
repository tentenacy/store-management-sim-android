package com.tenutz.storemngsim.ui.menu.optionmenu.optiongroup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.datasource.api.dto.common.CommonCondition
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
    savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    companion object {
        const val EVENT_NAVIGATE_UP = 1000
    }

    private val query = MutableLiveData("")

    private val _omOptionGroups = MutableLiveData<OptionOptionGroupsResponse>()
    val omOptionGroups: LiveData<OptionOptionGroupsResponse> = _omOptionGroups

    private val args = OmOptionGroupsFragmentArgs.fromSavedStateHandle(savedStateHandle)

    init {
        omOptionGroups()
    }

    fun omOptionGroups(
        searchText: String? = null,
        callback: () -> Unit = {}
    ) {
        searchText?.let { query.value = it }
        optionRepository.optionOptionGroups(args.optionMenu.optionCode, CommonCondition(query = query.value))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        _omOptionGroups.value = it
                        callback()
                    },
                    onFailure = {
                        Logger.e("$it")
                    }
                )
            }.addTo(compositeDisposable)
    }

    fun mapToOptionGroups(
        request: OptionGroupsMappedByRequest,
        callback: () -> Unit,
    ) {
        optionRepository.mapToOptionGroups(
            args.optionMenu.optionCode,
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