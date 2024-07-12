package com.tenutz.storemngsim.ui.menu.category.middle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.datasource.api.dto.category.MainCategoriesResponse
import com.tenutz.storemngsim.data.datasource.api.dto.category.MiddleCategoriesResponse
import com.tenutz.storemngsim.data.repository.category.CategoryRepository
import com.tenutz.storemngsim.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MiddleCategoriesViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
) : BaseViewModel() {

    private val _middleCategories = MutableLiveData<MiddleCategoriesResponse>()
    val middleCategories: LiveData<MiddleCategoriesResponse> = _middleCategories

    fun middleCategories(mainCateCd: String) {
        categoryRepository.middleCategories(mainCateCd)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result: Result<MiddleCategoriesResponse> ->
                result.fold(
                    onSuccess = {
                        _middleCategories.value = it
                    },
                    onFailure = {
                        Logger.e("$it")
                    },
                )
            }
    }
}