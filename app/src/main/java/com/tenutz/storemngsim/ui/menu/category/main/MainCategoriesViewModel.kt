package com.tenutz.storemngsim.ui.menu.category.main

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
class MainCategoriesViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
) : BaseViewModel() {

    private val query = MutableLiveData("")

    private val _mainCategories = MutableLiveData<MainCategoriesResponse>()
    val mainCategories: LiveData<MainCategoriesResponse> = _mainCategories

    fun mainCategories(searchText: String? = null) {
        searchText?.let { query.value = it }
        categoryRepository.mainCategories(CommonCondition(query = query.value))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result: Result<MainCategoriesResponse> ->
                result.fold(
                    onSuccess = {
                        _mainCategories.value = it
                    },
                    onFailure = {
                        Logger.e("$it")
                    },
                )
            }.addTo(compositeDisposable)
    }
}