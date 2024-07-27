package com.tenutz.storemngsim.ui.menu.category.sub

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.orhanobut.logger.Logger
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
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _subCategory = MutableLiveData<SubCategoryResponse>()
    val subCategory: LiveData<SubCategoryResponse> = _subCategory

    private val _editMode = MutableLiveData<Boolean>(false)
    val editMode: LiveData<Boolean> = _editMode

    private val args =
        SubCategoryDetailsFragmentArgs.fromSavedStateHandle(savedStateHandle)

    init {
        subCategory()
    }

    fun switchToEditMode() {
        _editMode.value = true
    }

    fun switchToReadMode() {
        _editMode.value = false
    }

    fun subCategory(mainCateCd: String = "2000", middleCateCd: String = "3000", callback: () -> Unit = {}) {
        categoryRepository.subCategory(mainCateCd, middleCateCd, args.subCategoryCode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        _subCategory.value = it
                        callback()
                    },
                    onFailure = {
                        Logger.e("$it")
                    },
                )
            }.addTo(compositeDisposable)
    }

    fun updateSubCategory(mainCateCd: String = "2000", middleCateCd: String = "3000", request: SubCategoryUpdateRequest, callback: () -> Unit = {}) {
        categoryRepository.updateSubCategory(mainCateCd, middleCateCd, args.subCategoryCode, request)
            .flatMap {
                categoryRepository.subCategory(mainCateCd, middleCateCd, args.subCategoryCode)
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