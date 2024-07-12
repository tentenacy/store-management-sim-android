package com.tenutz.storemngsim.application.manager

import com.facebook.login.LoginManager
import com.orhanobut.logger.Logger
import javax.inject.Inject

class FacebookLoginManager @Inject constructor(
    private val loginManager: LoginManager
) : OAuthLoginManagerSubject() {
    override fun logout() {
        loginManager.logOut()
        Logger.d("logout")
    }

    override fun withdrawal() {
    }
}