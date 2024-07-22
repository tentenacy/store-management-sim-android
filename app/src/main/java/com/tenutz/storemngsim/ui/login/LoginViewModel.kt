package com.tenutz.storemngsim.ui.login

import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.application.manager.OAuthLoginManagerSubject
import com.tenutz.storemngsim.data.datasource.api.err.ErrorCode
import com.tenutz.storemngsim.data.datasource.sharedpref.OAuthToken
import com.tenutz.storemngsim.data.datasource.sharedpref.Token
import com.tenutz.storemngsim.data.repository.user.UserRepository
import com.tenutz.storemngsim.ui.base.BaseViewModel
import com.tenutz.storemngsim.ui.base.Loginable
import com.tenutz.storemngsim.ui.signup.SignupSuccessViewModel
import com.tenutz.storemngsim.utils.ext.toErrorResponseOrNull
import com.tenutz.storemngsim.utils.type.SocialType
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val oauthLoginManagerMap: Map<String, @JvmSuppressWildcards OAuthLoginManagerSubject>,
): BaseViewModel(), Loginable {

    companion object {

        const val EVENT_NAVIGATE_TO_MAIN = 1000
        const val EVENT_NAVIGATE_TO_SIGNUP = 1001
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

                viewEvent(Pair(EVENT_NAVIGATE_TO_MAIN, Unit))
            }) { t ->
                Logger.e("${t}")

                t.toErrorResponseOrNull()?.let {
                    return@let when(it.code) {
                        ErrorCode.USER_NOT_FOUND.code -> {
                            viewEvent(Pair(EVENT_NAVIGATE_TO_SIGNUP, Pair(accessToken, socialType.name)))
                            return@let false
                        }
                        else -> {
                            true
                        }
                    }
                }?.takeIf { it }?.run {
                    logout()
                }
            }.addTo(compositeDisposable)
    }

    override fun logout() {
        oauthLoginManagerMap[OAuthToken.socialType]?.logout()
        OAuthToken.clear()
        Token.clear()
    }
}