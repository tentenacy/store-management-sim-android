package com.tenutz.storemngsim.ui.menu.category.middle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.datasource.api.dto.category.MainCategoryUpdateRequest
import com.tenutz.storemngsim.data.datasource.api.dto.category.MiddleCategoriesResponse
import com.tenutz.storemngsim.data.datasource.api.dto.category.MiddleCategoryResponse
import com.tenutz.storemngsim.data.datasource.api.dto.category.MiddleCategoryUpdateRequest
import com.tenutz.storemngsim.data.repository.category.CategoryRepository
import com.tenutz.storemngsim.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MiddleCategoryDetailsViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
) : BaseViewModel() {

    private val _middleCategory = MutableLiveData<MiddleCategoryResponse>()
    val middleCategory: LiveData<MiddleCategoryResponse> = _middleCategory

    private val _editMode = MutableLiveData<Boolean>(false)
    val editMode: LiveData<Boolean> = _editMode

    fun switchToEditMode() {
        _editMode.value = true
    }

    fun switchToReadMode() {
        _editMode.value = false
    }

    fun middleCategory(mainCateCd: String, middleCateCd: String) {
        categoryRepository.middleCategory(mainCateCd, middleCateCd)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result: Result<MiddleCategoryResponse> ->
                result.fold(
                    onSuccess = {
                        _middleCategory.value = it
                    },
                    onFailure = {
                        Logger.e("$it")
                    },
                )
            }
    }

    fun updateMiddleCategory(mainCateCd: String, middleCateCd: String, request: MiddleCategoryUpdateRequest, callback: () -> Unit) {
        categoryRepository.updateMiddleCategory(mainCateCd, middleCateCd, request)
            .flatMap {
                categoryRepository.middleCategory(mainCateCd, middleCateCd)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        _middleCategory.value = it
                        callback()
                        switchToReadMode()
                    },
                    onFailure = {
                        Logger.e("$it")
                    },
                )
            }.addTo(compositeDisposable)
    }
}