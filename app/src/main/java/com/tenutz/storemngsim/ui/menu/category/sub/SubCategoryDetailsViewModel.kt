package com.tenutz.storemngsim.ui.menu.category.sub

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.datasource.api.dto.category.MiddleCategoryResponse
import com.tenutz.storemngsim.data.datasource.api.dto.category.MiddleCategoryUpdateRequest
import com.tenutz.storemngsim.data.datasource.api.dto.category.SubCategoryResponse
import com.tenutz.storemngsim.data.datasource.api.dto.category.SubCategoryUpdateRequest
import com.tenutz.storemngsim.data.repository.category.CategoryRepository
import com.tenutz.storemngsim.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class SubCategoryDetailsViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
) : BaseViewModel() {

    private val _subCategory = MutableLiveData<SubCategoryResponse>()
    val subCategory: LiveData<SubCategoryResponse> = _subCategory

    private val _editMode = MutableLiveData<Boolean>(false)
    val editMode: LiveData<Boolean> = _editMode

    fun switchToEditMode() {
        _editMode.value = true
    }

    fun switchToReadMode() {
        _editMode.value = false
    }

    fun subCategory(mainCateCd: String, middleCateCd: String, subCateCd: String) {
        categoryRepository.subCategory(mainCateCd, middleCateCd, subCateCd)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        _subCategory.value = it
                    },
                    onFailure = {
                        Logger.e("$it")
                    },
                )
            }
    }

    fun updateSubCategory(mainCateCd: String, middleCateCd: String, subCateCd: String, request: SubCategoryUpdateRequest, callback: () -> Unit) {
        categoryRepository.updateSubCategory(mainCateCd, middleCateCd, subCateCd, request)
            .flatMap {
                categoryRepository.subCategory(mainCateCd, middleCateCd, subCateCd)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        _subCategory.value = it
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