package com.tenutz.storemngsim.application

import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.network.authenticator.TokenAuthenticator
import com.tenutz.storemngsim.network.observer.TokenExpirationObserver
import com.tenutz.storemngsim.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GlobalViewModel @Inject constructor(
    private val tokenAuthenticator: TokenAuthenticator,
): BaseViewModel(), TokenExpirationObserver {

    init {
        tokenAuthenticator.registerObserver(this)
    }

    override fun onRefreshTokenExpired() {
        Logger.d("onRefreshTokenExpired")
    }

    override fun onCleared() {
        tokenAuthenticator.unregisterObserver(this)
        super.onCleared()
    }
}