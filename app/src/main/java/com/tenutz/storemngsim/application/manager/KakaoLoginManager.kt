package com.tenutz.storemngsim.application.manager

import com.kakao.sdk.user.UserApiClient
import com.orhanobut.logger.Logger
import javax.inject.Inject

class KakaoLoginManager @Inject constructor(
    private val userApiClient: UserApiClient,
) : OAuthLoginManagerSubject() {
    override fun logout() {
        userApiClient.logout { error ->
            if (error == null) {
                Logger.d("logout successful")
            } else {
                Logger.e("error: ${error?.message}")
            }
        }
    }

    override fun withdrawal() {
    }
}