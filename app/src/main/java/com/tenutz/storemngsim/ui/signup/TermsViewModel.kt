package com.tenutz.storemngsim.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.datasource.api.dto.terms.TermsDetailsResponse
import com.tenutz.storemngsim.data.datasource.api.dto.terms.TermsResponse
import com.tenutz.storemngsim.data.repository.terms.TermsRepository
import com.tenutz.storemngsim.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class TermsViewModel @Inject constructor(
    private val termsRepository: TermsRepository,
): BaseViewModel() {

    private val _termsDetails = MutableLiveData<TermsDetailsResponse>()
    val termsDetails: LiveData<TermsDetailsResponse> = _termsDetails

/*
    fun terms() {
        termsRepository.terms()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        _terms.value = it
                    },
                    onFailure = {
                        Logger.e("$it")
                    },
                )
            }
    }
*/

    fun termsDetails(termsCode: String) {
        termsRepository.termsDetails(termsCode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        _termsDetails.value = it
                    },
                    onFailure = {
                        Logger.e("$it")
                    },
                )
            }
    }
}