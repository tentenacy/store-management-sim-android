package com.tenutz.storemngsim.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.datasource.api.dto.store.StatisticsSalesByMenusTodayResponse
import com.tenutz.storemngsim.data.datasource.api.dto.store.StoreMainResponse
import com.tenutz.storemngsim.data.datasource.api.dto.user.UserDetailsResponse
import com.tenutz.storemngsim.data.repository.store.StoreRepository
import com.tenutz.storemngsim.data.repository.user.UserRepository
import com.tenutz.storemngsim.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val storeRepository: StoreRepository,
): BaseViewModel() {

    private val _storeMain = MutableLiveData<StoreMainResponse>()
    val storeMain: LiveData<StoreMainResponse> = _storeMain

    private val _pieMenus = MutableLiveData<StatisticsSalesByMenusTodayResponse>()
    val pieMenus: LiveData<StatisticsSalesByMenusTodayResponse> = _pieMenus

    fun storeMain(callback: (String, String) -> Unit = { _, _ -> }) {
        storeRepository.storeMain()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        _storeMain.value = it
                        callback(it.mainCategoryCode, it.middleCategoryCode)
                    },
                    onFailure = {
                        Logger.e("${it.message}")
                    },
                )
            }.addTo(compositeDisposable)
    }

    fun pieMenus() {
        storeRepository.statisticsSalesByMenuToday()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        _pieMenus.value = it
                    },
                    onFailure = {
                        Logger.e("${it.message}")
                    },
                )
            }.addTo(compositeDisposable)
    }
}