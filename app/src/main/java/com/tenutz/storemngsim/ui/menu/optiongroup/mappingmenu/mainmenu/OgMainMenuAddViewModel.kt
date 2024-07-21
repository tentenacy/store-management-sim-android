package com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.mainmenu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.storemngsim.data.datasource.api.dto.common.MainMenuSearchRequest
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.MainMenusMappedByRequest
import com.tenutz.storemngsim.data.repository.optiongroup.OptionGroupRepository
import com.tenutz.storemngsim.ui.base.BaseViewModel
import com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.mainmenu.args.OgMainMenuAddArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class OgMainMenuAddViewModel @Inject constructor(
    private val optionGroupRepository: OptionGroupRepository,
) : BaseViewModel() {

    companion object {
        const val EVENT_NAVIGATE_UP = 1000
    }

    private val query = MutableLiveData("")

    private val _ogMainMenusAdd = MutableLiveData<OgMainMenuAddArgs>()
    val ogMainMenusAdd: LiveData<OgMainMenuAddArgs> = _ogMainMenusAdd

    private val _expandedItemCount = MutableLiveData(0)
    val expandedItemCount: LiveData<Int> = _expandedItemCount

    fun updateExpandedItemCount() {
        _ogMainMenusAdd.value?.let {
            _expandedItemCount.value = it.ogMainMenus.count { it.expanded }
        }
    }

    fun ogMainMenusAdd(
        optionGroupCd: String,
        request: MainMenuSearchRequest,
        searchText: String? = null,
    ) {
        searchText?.let { query.value = it }
        optionGroupRepository.optionGroupMainMenus(optionGroupCd, request, CommonCondition(query = query.value))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        _ogMainMenusAdd.value = OgMainMenuAddArgs(
                            it.optionGroupMainMenus.map {
                                OgMainMenuAddArgs.OptionGroupMainMenus(
                                    it.storeCode,
                                    it.menuCode,
                                    it.menuName,
                                    it.imageUrl,
                                    it.outOfStock,
                                    it.price,
                                    it.discountingPrice,
                                    it.discountedPrice,
                                    it.use,
                                    it.mainCategoryCode,
                                    it.middleCategoryCode,
                                    it.subCategoryCode,
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

    fun mapToMainMenus(
        optionGroupCd: String,
        request: MainMenusMappedByRequest,
        callback: () -> Unit,
    ) {
        optionGroupRepository.mapToMainMenus(
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