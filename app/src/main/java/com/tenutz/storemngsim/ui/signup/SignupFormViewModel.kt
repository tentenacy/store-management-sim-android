package com.tenutz.storemngsim.ui.signup

import android.view.View
import android.widget.CheckBox
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.application.manager.OAuthLoginManagerSubject
import com.tenutz.storemngsim.data.datasource.api.dto.user.SignupRequest
import com.tenutz.storemngsim.data.datasource.api.dto.user.SocialSignupRequest
import com.tenutz.storemngsim.data.datasource.api.err.ErrorCode
import com.tenutz.storemngsim.data.datasource.sharedpref.OAuthToken
import com.tenutz.storemngsim.data.datasource.sharedpref.Token
import com.tenutz.storemngsim.data.repository.user.UserRepository
import com.tenutz.storemngsim.ui.base.BaseViewModel
import com.tenutz.storemngsim.ui.base.Loginable
import com.tenutz.storemngsim.ui.login.LoginViewModel
import com.tenutz.storemngsim.utils.ext.toErrorResponseOrNull
import com.tenutz.storemngsim.utils.type.SocialType
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class SignupFormViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val oauthLoginManagerMap: Map<String, @JvmSuppressWildcards OAuthLoginManagerSubject>,
): BaseViewModel(), Loginable {

    companion object {
        const val EVENT_TOAST = 1000
        const val EVENT_NAVIGATE_TO_SIGNUP_SUCCESS = 1001
        const val EVENT_SOCIAL_LOGIN = 1002
    }

    private val _terms1Check = MutableLiveData(false)
    val terms1Check: LiveData<Boolean> = _terms1Check

    private val _terms2Check = MutableLiveData(false)
    val terms2Check: LiveData<Boolean> = _terms2Check

    val phoneNumber = MutableLiveData("")

    private fun _termsCheckAll(): Boolean = (terms2Check.value == true) and (terms1Check.value == true)

    val termsCheckAll: MediatorLiveData<Boolean> = MediatorLiveData()

    init {
        termsCheckAll.addSource(_terms1Check) {
            termsCheckAll.value = _termsCheckAll()
        }
        termsCheckAll.addSource(_terms2Check) {
            termsCheckAll.value = _termsCheckAll()
        }
    }

    fun checkOrUncheckTermsAll(view: View?) {
        if(view is CheckBox) {
            _terms1Check.value = view.isChecked
            _terms2Check.value = view.isChecked
        }
    }

    fun checkTerms1() {
        _terms1Check.value = true
    }

    fun checkTerms2() {
        _terms2Check.value = true
    }

    fun checkOrUncheckTerms1() {
        _terms1Check.value = _terms1Check.value != true
    }

    fun checkOrUncheckTerm2() {
        _terms2Check.value = _terms2Check.value != true
    }

    fun signup(socialType: String, request: SocialSignupRequest) {
        userRepository.socialSignup(socialType, request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    viewEvent(Pair(EVENT_SOCIAL_LOGIN, SocialType.of(socialType)))
                },
                {
                    Logger.e("$it")

                    it.toErrorResponseOrNull()?.let {
                        when(it.code) {
                            ErrorCode.SOCIAL_COMMUNICATION_ERROR.code -> {
                                viewEvent(Pair(EVENT_TOAST, ErrorCode.SOCIAL_COMMUNICATION_ERROR.message))
                            }
                            ErrorCode.ALREADY_SIGNEDUP.code -> {
                                viewEvent(Pair(EVENT_TOAST, ErrorCode.ALREADY_SIGNEDUP.message))
                            }
                            ErrorCode.STORE_MASTER_NOT_FOUND.code, ErrorCode.MANAGER_NOT_FOUND.code -> {
                                viewEvent(Pair(EVENT_TOAST, "등록되지 않은 사용자입니다."))
                            }
                        }
                    }
                }
            ).addTo(compositeDisposable)
    }

    override fun socialLogin(accessToken: String, socialType: SocialType) {
        userRepository.socialLogin(socialType)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->

                Token.save(
                    grantType = response.grantType,
                    accessToken = response.accessToken,
                    refreshToken = response.refreshToken,
                    accessTokenExpireDate = response.accessTokenExpiresIn,
                )

                viewEvent(Pair(EVENT_NAVIGATE_TO_SIGNUP_SUCCESS, Unit))
            }) { t ->
                Logger.e("${t}")
                logout()
            }.addTo(compositeDisposable)
    }


    override fun logout() {
        oauthLoginManagerMap[OAuthToken.socialType]?.logout()
        OAuthToken.clear()
        Token.clear()
    }
}