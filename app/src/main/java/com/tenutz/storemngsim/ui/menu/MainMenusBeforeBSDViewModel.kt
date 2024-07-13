package com.tenutz.storemngsim.ui.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.datasource.api.dto.category.MainCategoriesResponse
import com.tenutz.storemngsim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.storemngsim.data.repository.category.CategoryRepository
import com.tenutz.storemngsim.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainMenusBeforeBSDViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
) : BaseViewModel() {

    companion object {

        const val EVENT_PICK_MAIN_CATEGORY = 1000
        const val EVENT_PICK_MIDDLE_CATEGORY = 1001
        const val EVENT_PICK_SUB_CATEGORY = 1002
    }

    private val _mainCategoryCode = MutableLiveData<Pair<String?, String?>?>()
    val mainCategoryCode: LiveData<Pair<String?, String?>?> = _mainCategoryCode

    private val _middleCategoryCode = MutableLiveData<Pair<String?, String?>?>()
    val middleCategoryCode: LiveData<Pair<String?, String?>?> = _middleCategoryCode

    private val _subCategoryCode = MutableLiveData<Pair<String?, String?>?>()
    val subCategoryCode: LiveData<Pair<String?, String?>?> = _subCategoryCode

    fun setMainCategoryCode(pair: Pair<String?, String?>) {
        _mainCategoryCode.value = pair
        _middleCategoryCode.value = null
        _subCategoryCode.value = null
    }

    fun setMiddleCategoryCode(pair: Pair<String?, String?>) {
        _middleCategoryCode.value = pair
        _subCategoryCode.value = null
    }

    fun setSubCategoryCode(pair: Pair<String?, String?>) {
        _subCategoryCode.value = pair
    }

    fun mainCategoryPickerEvent() {
        categoryRepository.mainCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result: Result<MainCategoriesResponse> ->
                result.fold(
                    onSuccess = {
                        viewEvent(Pair(EVENT_PICK_MAIN_CATEGORY, it.mainCategories.associate { it.categoryCode to it.categoryName }))
                    },
                    onFailure = {
                        Logger.e("$it")
                    },
                )
            }.addTo(compositeDisposable)
    }

    fun middleCategoryPickerEvent() {
        mainCategoryCode.value?.let {
            categoryRepository.middleCategories(it.first!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result ->
                    result.fold(
                        onSuccess = {
                            viewEvent(Pair(EVENT_PICK_MIDDLE_CATEGORY, it.middleCategories.associate { it.categoryCode to it.categoryName }))
                        },
                        onFailure = {
                            Logger.e("$it")
                        },
                    )
                }.addTo(compositeDisposable)
        }
    }

    fun subCategoryPickerEvent() {
        listOf(mainCategoryCode.value, middleCategoryCode.value).takeIf { it.all { it?.first != null } }?.filterNotNull()?.let {
            categoryRepository.subCategories(it[0].first!!, it[1].first!!)
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
}