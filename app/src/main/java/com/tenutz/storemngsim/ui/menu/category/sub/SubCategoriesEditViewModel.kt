package com.tenutz.storemngsim.ui.menu.category.sub

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.datasource.api.dto.category.CategoriesDeleteRequest
import com.tenutz.storemngsim.data.datasource.api.dto.category.CategoryPrioritiesChangeRequest
import com.tenutz.storemngsim.data.datasource.api.dto.category.MiddleCategoriesResponse
import com.tenutz.storemngsim.data.datasource.api.dto.category.SubCategoriesResponse
import com.tenutz.storemngsim.data.repository.category.CategoryRepository
import com.tenutz.storemngsim.ui.base.BaseViewModel
import com.tenutz.storemngsim.ui.menu.category.middle.args.MiddleCategoriesEditArgs
import com.tenutz.storemngsim.ui.menu.category.sub.args.SubCategoriesEditArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class SubCategoriesEditViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
) : BaseViewModel() {

    companion object {
        const val EVENT_NAVIGATE_UP = 1000
    }

    private val _subCategoriesEdit = MutableLiveData<SubCategoriesEditArgs>()
    val subCategoriesEdit: LiveData<SubCategoriesEditArgs> = _subCategoriesEdit

    private val _checkedItemCount = MutableLiveData(0)
    val checkedItemCount: LiveData<Int> = _checkedItemCount

    fun updateCheckedItemCount() {
        subCategoriesEdit.value?.let {
            _checkedItemCount.value = it.subCategoriesEdit.count { it.checked }
        }
    }

    fun setSubCategoriesEdit(args: SubCategoriesResponse) {
        _subCategoriesEdit.value = SubCategoriesEditArgs(
            args.subCategories.map {
                SubCategoriesEditArgs.SubCategoryEdit(
                    it.storeCode,
                    it.mainCategoryCode,
                    it.middleCategoryCode,
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

    fun deleteSubCategories(mainCateCd: String, middleCateCd: String, request: CategoriesDeleteRequest, callback: () -> Unit) {
        categoryRepository.deleteSubCategories(mainCateCd, middleCateCd, request)
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

    fun changeSubCategoryPriorities(mainCateCd: String, middleCateCd: String, request: CategoryPrioritiesChangeRequest, callback: () -> Unit) {
        categoryRepository.changeSubCategoryPriorities(mainCateCd, middleCateCd, request)
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