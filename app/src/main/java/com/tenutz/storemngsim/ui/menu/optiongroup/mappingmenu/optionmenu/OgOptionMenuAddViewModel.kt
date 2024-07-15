package com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.optionmenu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.OptionsMappedByRequest
import com.tenutz.storemngsim.data.repository.optiongroup.OptionGroupRepository
import com.tenutz.storemngsim.ui.base.BaseViewModel
import com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.mainmenu.args.OgOptionMenuAddArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class OgOptionMenuAddViewModel @Inject constructor(
    private val optionGroupRepository: OptionGroupRepository,
) : BaseViewModel() {

    companion object {
        const val EVENT_NAVIGATE_UP = 1000
    }

    private val query = MutableLiveData("")

    private val _ogOptionMenusAdd = MutableLiveData<OgOptionMenuAddArgs>()
    val ogOptionMenusAdd: LiveData<OgOptionMenuAddArgs> = _ogOptionMenusAdd

    fun ogOptionMenusAdd(
        optionGroupCd: String,
        searchText: String? = null,
    ) {
        searchText?.let { query.value = it }
        optionGroupRepository.optionGroupOptions(optionGroupCd, CommonCondition(query = query.value))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        _ogOptionMenusAdd.value = OgOptionMenuAddArgs(
                            it.optionGroupOptions.map {
                                OgOptionMenuAddArgs.OptionGroupOptionMenus(
                                    it.storeCode,
                                    it.optionCode,
                                    it.optionName,
                                    it.price,
                                )
                            }
                        )
                    },
                    onFailure = {
                        Logger.e("$it")
                    }
                )
            }.addTo(compositeDisposable)
    }

    fun mapToOptionMenus(
        optionGroupCd: String,
        request: OptionsMappedByRequest,
        callback: () -> Unit,
    ) {
        optionGroupRepository.mapToOptions(
            optionGroupCd,
            request,
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