package com.tenutz.storemngsim.ui.menu.category.sub

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.datasource.api.dto.category.MiddleCategoryResponse
import com.tenutz.storemngsim.data.datasource.api.dto.category.SubCategoriesResponse
import com.tenutz.storemngsim.data.repository.category.CategoryRepository
import com.tenutz.storemngsim.di.qualifier.NavigationGraphReference
import com.tenutz.storemngsim.di.qualifier.NavigationGraphs
import com.tenutz.storemngsim.ui.base.BaseViewModel
import com.tenutz.storemngsim.usecase.PutSharedDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class SubCategoriesViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
) : BaseViewModel() {

    private val _subCategories = MutableLiveData<SubCategoriesResponse>()
    val subCategories: LiveData<SubCategoriesResponse> = _subCategories

    init {
        subCategories()
    }

    fun subCategories(mainCateCd: String = "2000", middleCateCd: String = "3000") {
        categoryRepository.subCategories(mainCateCd, middleCateCd)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result: Result<SubCategoriesResponse> ->
                result.fold(
                    onSuccess = {
                        _subCategories.value = it
                    },
                    onFailure = {
                        Logger.e("$it")
                    },
                )
            }.addTo(compositeDisposable)
    }
}