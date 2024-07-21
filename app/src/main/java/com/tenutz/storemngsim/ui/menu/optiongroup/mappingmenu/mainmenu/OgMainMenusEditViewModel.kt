package com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.mainmenu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.OptionGroupMainMenuMapperPrioritiesChangeRequest
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.OptionGroupMainMenuMappersDeleteRequest
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.OptionGroupMainMenuMappersResponse
import com.tenutz.storemngsim.data.repository.optiongroup.OptionGroupRepository
import com.tenutz.storemngsim.ui.base.BaseViewModel
import com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.mainmenu.args.OgMainMenusEditArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class OgMainMenusEditViewModel @Inject constructor(
    private val optionGroupRepository: OptionGroupRepository,
) : BaseViewModel() {

    companion object {
        const val EVENT_NAVIGATE_UP = 1000
    }

    private val _ogMainMenusEdit = MutableLiveData<OgMainMenusEditArgs>()
    val ogMainMenusEdit: LiveData<OgMainMenusEditArgs> = _ogMainMenusEdit

    private val _checkedItemCount = MutableLiveData(0)
    val checkedItemCount: LiveData<Int> = _checkedItemCount

    fun updateCheckedItemCount() {
        ogMainMenusEdit.value?.let {
            _checkedItemCount.value = it.ogMainMenuMappersEdit.count { it.checked }
        }
    }

    fun setOgMainMenusEdit(args: OptionGroupMainMenuMappersResponse) {
        _ogMainMenusEdit.value = OgMainMenusEditArgs(
            args.optionGroupMainMenuMappers.map {
                OgMainMenusEditArgs.OptionGroupMainMenuMapper(
                    it.storeCode,
                    it.mainCategoryCode,
                    it.middleCategoryCode,
                    it.subCategoryCode,
                    it.menuCode,
                    it.menuName,
                    it.imageUrl,
                    it.outOfStock,
                    it.price,
                    it.discountingPrice,
                    it.discountedPrice,
                    it.use,
                    it.priority,
                )
            }
        )
    }

    fun deleteOgMainMenus(optionGroupCd: String, request: OptionGroupMainMenuMappersDeleteRequest, callback: () -> Unit) {
        optionGroupRepository.deleteOptionGroupMainMenuMappers(optionGroupCd, request)
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

    fun changeMiddleCategoryPriorities(mainCateCd: String, request: OptionGroupMainMenuMapperPrioritiesChangeRequest, callback: () -> Unit) {
        optionGroupRepository.changeOptionGroupMainMenuMapperPriorities(mainCateCd, request)
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