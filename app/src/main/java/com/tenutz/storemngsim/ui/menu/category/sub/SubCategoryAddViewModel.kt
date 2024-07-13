package com.tenutz.storemngsim.ui.menu.category.sub

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.datasource.api.dto.category.MainCategoriesResponse
import com.tenutz.storemngsim.data.datasource.api.dto.category.MainCategoryCreateRequest
import com.tenutz.storemngsim.data.datasource.api.dto.category.MiddleCategoryCreateRequest
import com.tenutz.storemngsim.data.datasource.api.dto.category.SubCategoryCreateRequest
import com.tenutz.storemngsim.data.repository.category.CategoryRepository
import com.tenutz.storemngsim.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class SubCategoryAddViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
) : BaseViewModel() {

    companion object {
        const val EVENT_NAVIGATE_UP = 1000
    }

    fun createSubCategory(mainCateCd: String, middleCateCd: String, request: SubCategoryCreateRequest, callback: () -> Unit) {
        categoryRepository.createSubCategory(mainCateCd, middleCateCd, request)
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
                    },
                )
            }.addTo(compositeDisposable)
    }
}