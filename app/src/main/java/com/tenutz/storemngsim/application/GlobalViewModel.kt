package com.tenutz.storemngsim.application

import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.application.manager.OAuthLoginManagerSubject
import com.tenutz.storemngsim.data.datasource.sharedpref.OAuthToken
import com.tenutz.storemngsim.data.datasource.sharedpref.Token
import com.tenutz.storemngsim.network.authenticator.TokenAuthenticator
import com.tenutz.storemngsim.network.observer.TokenExpirationObserver
import com.tenutz.storemngsim.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GlobalViewModel @Inject constructor(
    private val tokenAuthenticator: TokenAuthenticator,
    private val oauthLoginManagerMap: Map<String, @JvmSuppressWildcards OAuthLoginManagerSubject>,
): BaseViewModel(), TokenExpirationObserver {

    companion object {
        const val EVENT_GLOBAL_NAVIGATE_TO_LOGIN = 1000
    }
    
    init {
        tokenAuthenticator.registerObserver(this)
    }

    override fun onRefreshTokenExpired() {
        Logger.d("onRefreshTokenExpired")
        logout(true)
    }

    override fun onCleared() {
        tokenAuthenticator.unregisterObserver(this)
        super.onCleared()
    }

    fun logout(refreshTokenExpired: Boolean = false) {
        oauthLoginManagerMap[OAuthToken.socialType]?.logout()
        OAuthToken.clear()
        Token.clear()
        viewEvent(Pair(EVENT_GLOBAL_NAVIGATE_TO_LOGIN, refreshTokenExpired))
    }
}