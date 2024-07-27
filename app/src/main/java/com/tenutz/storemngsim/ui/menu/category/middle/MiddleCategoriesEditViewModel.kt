package com.tenutz.storemngsim.ui.menu.category.middle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.datasource.api.dto.category.CategoriesDeleteRequest
import com.tenutz.storemngsim.data.datasource.api.dto.category.CategoryPrioritiesChangeRequest
import com.tenutz.storemngsim.data.datasource.api.dto.category.MiddleCategoriesResponse
import com.tenutz.storemngsim.data.repository.category.CategoryRepository
import com.tenutz.storemngsim.ui.base.BaseViewModel
import com.tenutz.storemngsim.ui.menu.category.middle.args.MiddleCategoriesEditArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MiddleCategoriesEditViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
) : BaseViewModel() {

    companion object {
        const val EVENT_NAVIGATE_UP = 1000
    }

    private val _middleCategoriesEdit = MutableLiveData<MiddleCategoriesEditArgs>()
    val middleCategoriesEdit: LiveData<MiddleCategoriesEditArgs> = _middleCategoriesEdit

    private val _checkedItemCount = MutableLiveData(0)
    val checkedItemCount: LiveData<Int> = _checkedItemCount

    fun updateCheckedItemCount() {
        middleCategoriesEdit.value?.let {
            _checkedItemCount.value = it.middleCategoriesEdit.count { it.checked }
        }
    }

    fun setMiddleCategoriesEdit(args: MiddleCategoriesResponse) {
        _middleCategoriesEdit.value = MiddleCategoriesEditArgs(
            args.middleCategories.map {
                MiddleCategoriesEditArgs.MiddleCategoryEdit(
                    it.storeCode,
                    it.mainCategoryCode,
                    it.categoryCode,
                    it.categoryName,
                    it.use,
                    it.imageName,
                    it.imageUrl,
                    it.order,
                    it.createdAt,
                    it.lastModifiedAt,
                )
            }
        )
    }

    fun deleteMiddleCategories(mainCateCd: String = "2000", request: CategoriesDeleteRequest, callback: () -> Unit) {
        categoryRepository.deleteMiddleCategories(mainCateCd, request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        viewEvent(Pair(EVENT_NAVIGATE_UP, Unit))
                        callback()
                    },
                    onFailure = {
                        Logger.e("$it")
                    },
                )
            }
            .addTo(compositeDisposable)
    }

    fun changeMiddleCategoryPriorities(mainCateCd: String = "2000", request: CategoryPrioritiesChangeRequest, callback: () -> Unit) {
        categoryRepository.changeMiddleCategoryPriorities(mainCateCd, request)
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