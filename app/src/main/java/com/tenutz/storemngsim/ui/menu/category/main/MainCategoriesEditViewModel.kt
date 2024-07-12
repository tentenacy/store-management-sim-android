package com.tenutz.storemngsim.ui.menu.category.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.datasource.api.dto.category.CategoriesDeleteRequest
import com.tenutz.storemngsim.data.datasource.api.dto.category.CategoryPrioritiesChangeRequest
import com.tenutz.storemngsim.data.datasource.api.dto.category.MainCategoriesResponse
import com.tenutz.storemngsim.data.repository.category.CategoryRepository
import com.tenutz.storemngsim.ui.base.BaseViewModel
import com.tenutz.storemngsim.ui.menu.category.main.args.MainCategoriesEditArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainCategoriesEditViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
) : BaseViewModel() {

    companion object {
        const val EVENT_NAVIGATE_UP = 1000
    }

    private val _mainCategoriesEdit = MutableLiveData<MainCategoriesEditArgs>()
    val mainCategoriesEdit: LiveData<MainCategoriesEditArgs> = _mainCategoriesEdit

    private val _checkedItemCount = MutableLiveData(0)
    val checkedItemCount: LiveData<Int> = _checkedItemCount

    fun updateCheckedItemCount() {
        mainCategoriesEdit.value?.let {
            _checkedItemCount.value = it.mainCategoriesEdit.count { it.checked }
        }
    }

    fun setMainCategoriesEdit(args: MainCategoriesResponse) {
        _mainCategoriesEdit.value = MainCategoriesEditArgs(
            args.mainCategories.map {
                MainCategoriesEditArgs.MainCategoryEdit(
                    it.storeCode,
                    it.categoryCode,
                    it.categoryName,
                    it.use,
                    it.order,
                    it.createdAt,
                    it.lastModifiedAt,
                )
            }
        )
    }

    fun deleteMainCategories(request: CategoriesDeleteRequest, callback: () -> Unit) {
        categoryRepository.deleteMainCategories(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                viewEvent(Pair(EVENT_NAVIGATE_UP, Unit))
                callback()
            }
            .addTo(compositeDisposable)
    }

    fun changeMainCategoryPriorities(request: CategoryPrioritiesChangeRequest, callback: () -> Unit) {
        categoryRepository.changeMainCategoryPriorities(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        callback()
                    },
                    onFailure = {
                        Logger.e("$it")
                    },
                )
            }.addTo(compositeDisposable)
    }
}