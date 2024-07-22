package com.tenutz.storemngsim.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.datasource.api.dto.store.StoreMainResponse
import com.tenutz.storemngsim.data.repository.store.StoreRepository
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

    fun setStoreMain(storeMain: StoreMainResponse) {
        _storeMain.value = storeMain
    }

    fun storeMain() {
        storeRepository.storeMain()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        _storeMain.value = it
                    },
                    onFailure = {
                        Logger.e("$it")
                    },
                )
            }.addTo(compositeDisposable)
    }

}