package com.tenutz.storemngsim.application.manager

import com.navercorp.nid.NaverIdLoginSDK
import com.orhanobut.logger.Logger
import javax.inject.Inject

class NaverLoginManager @Inject constructor() : OAuthLoginManagerSubject() {

    override fun logout() {
        NaverIdLoginSDK.logout()
        Logger.d("logout")
    }

    override fun withdrawal() {
    }
}