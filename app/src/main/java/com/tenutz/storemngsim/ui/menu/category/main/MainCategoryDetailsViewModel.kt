package com.tenutz.storemngsim.ui.menu.category.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.datasource.api.dto.category.MainCategoryResponse
import com.tenutz.storemngsim.data.datasource.api.dto.category.MainCategoryUpdateRequest
import com.tenutz.storemngsim.data.repository.category.CategoryRepository
import com.tenutz.storemngsim.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainCategoryDetailsViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
) : BaseViewModel() {

    private val _mainCategory = MutableLiveData<MainCategoryResponse>()
    val mainCategory: LiveData<MainCategoryResponse> = _mainCategory

    private val _editMode = MutableLiveData<Boolean>(false)
    val editMode: LiveData<Boolean> = _editMode

    fun switchToEditMode() {
        _editMode.value = true
    }

    fun switchToReadMode() {
        _editMode.value = false
    }

    fun mainCategory(mainCateCd: String = "2000") {
        categoryRepository.mainCategory(mainCateCd)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        _mainCategory.value = it
                    },
                    onFailure = {
                        Logger.e("$it")
                    },
                )
            }.addTo(compositeDisposable)
    }

    fun updateMainCategory(mainCateCd: String = "2000", request: MainCategoryUpdateRequest, callback: () -> Unit) {
        categoryRepository.updateMainCategory(mainCateCd, request)
            .flatMap {
                categoryRepository.mainCategory(mainCateCd)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        _mainCategory.value = it
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