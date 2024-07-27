package com.tenutz.storemngsim.ui.menu.category.main

import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.datasource.api.dto.category.MainCategoryCreateRequest
import com.tenutz.storemngsim.data.datasource.api.err.ErrorCode
import com.tenutz.storemngsim.data.repository.category.CategoryRepository
import com.tenutz.storemngsim.ui.base.BaseViewModel
import com.tenutz.storemngsim.utils.ext.toErrorResponseOrNull
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainCategoryAddViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
) : BaseViewModel() {

    companion object {
        const val EVENT_NAVIGATE_UP = 1000
        const val EVENT_TOAST = 1001
    }

    fun createMainCategory(request: MainCategoryCreateRequest, callback: () -> Unit) {
        categoryRepository.createMainCategory(request)
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
                        it.toErrorResponseOrNull()?.let {
                            when(it.code) {
                                ErrorCode.ALREADY_CATEGORY_CREATED.code -> {
                                    viewEvent(Pair(EVENT_TOAST, "중복된 카테고리 코드입니다."))
                                }
                            }
                        }
                    },
                )
            }.addTo(compositeDisposable)
    }
}