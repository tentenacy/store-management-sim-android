package com.tenutz.storemngsim.ui.menu.optiongroup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.datasource.api.dto.category.MainCategoriesResponse
import com.tenutz.storemngsim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.OptionGroupsResponse
import com.tenutz.storemngsim.data.repository.category.CategoryRepository
import com.tenutz.storemngsim.data.repository.optiongroup.OptionGroupRepository
import com.tenutz.storemngsim.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class OptionGroupsViewModel @Inject constructor(
    private val optionGroupRepository: OptionGroupRepository,
) : BaseViewModel() {

    private val query = MutableLiveData("")

    private val _optionGroups = MutableLiveData<OptionGroupsResponse>()
    val optionGroups: LiveData<OptionGroupsResponse> = _optionGroups

    fun optionGroups(searchText: String? = null) {
        searchText?.let { query.value = it }
        optionGroupRepository.optionGroups(CommonCondition(query = query.value))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result: Result<OptionGroupsResponse> ->
                result.fold(
                    onSuccess = {
                        _optionGroups.value = it
                    },
                    onFailure = {
                        Logger.e("$it")
                    },
                )
            }.addTo(compositeDisposable)
    }
}