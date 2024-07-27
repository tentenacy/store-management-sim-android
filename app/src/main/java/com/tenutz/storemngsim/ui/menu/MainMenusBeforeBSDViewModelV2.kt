package com.tenutz.storemngsim.ui.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.repository.category.CategoryRepository
import com.tenutz.storemngsim.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainMenusBeforeBSDViewModelV2 @Inject constructor(
    private val categoryRepository: CategoryRepository,
) : BaseViewModel() {

    companion object {
        const val EVENT_PICK_SUB_CATEGORY = 1002
    }

    private val _subCategoryCode = MutableLiveData<Pair<String?, String?>?>()
    val subCategoryCode: LiveData<Pair<String?, String?>?> = _subCategoryCode

    fun setSubCategoryCode(pair: Pair<String?, String?>) {
        _subCategoryCode.value = pair
    }

    fun subCategoryPickerEvent() {
        categoryRepository.subCategories("2000", "3000")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        viewEvent(Pair(EVENT_PICK_SUB_CATEGORY, it.subCategories.associate { it.categoryCode to it.categoryName }))
                    },
                    onFailure = {
                        Logger.e("$it")
                    },
                )
            }.addTo(compositeDisposable)
    }
}