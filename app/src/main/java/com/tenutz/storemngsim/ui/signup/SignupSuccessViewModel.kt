package com.tenutz.storemngsim.ui.signup

import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.repository.store.StoreRepository
import com.tenutz.storemngsim.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class SignupSuccessViewModel @Inject constructor(
    private val storeRepository: StoreRepository,
): BaseViewModel() {

    companion object {
        const val EVENT_NAVIGATE_TO_MAIN = 1000
    }

    fun eventNavigateToMain() {
        storeRepository.storeMain()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        viewEvent(Pair(EVENT_NAVIGATE_TO_MAIN, it))
                    },
                    onFailure = {
                        Logger.e("$it")
                    },
                )
            }.addTo(compositeDisposable)
    }
}