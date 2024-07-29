package com.tenutz.storemngsim.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
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
class SignupSuccessViewModel @Inject constructor(
    private val userRepository: UserRepository,
): BaseViewModel() {

    private val _details = MutableLiveData<UserDetailsResponse>()
    val details: LiveData<UserDetailsResponse> = _details

    init {
        profile()
    }

    fun profile() {
        userRepository.userDetails()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        _details.value = it
                    },
                    onFailure = {
                        Logger.e("$it")
                    },
                )
            }.addTo(compositeDisposable)
    }
}