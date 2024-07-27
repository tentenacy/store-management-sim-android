package com.tenutz.storemngsim.ui.login.handler

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.tenutz.storemngsim.ui.base.Loginable
import com.tenutz.storemngsim.ui.login.LoginFragment
import com.tenutz.storemngsim.ui.login.LoginViewModel
import com.tenutz.storemngsim.ui.login.args.SocialProfileArgs
import com.tenutz.storemngsim.ui.signup.SignupFormViewModel
import com.tenutz.storemngsim.utils.type.SocialType

class KakaoOAuthLoginHandler(private val fragment: Fragment): (OAuthToken?, Throwable?) -> Unit {

    private val viewModel: Loginable by lazy {
        ViewModelProvider(fragment).get(
            when(fragment) {
                is LoginFragment -> LoginViewModel::class.java
                else -> SignupFormViewModel::class.java
            }
        )
    }

    override fun invoke(token: OAuthToken?, error: Throwable?) {
        error?.let { onError(it) }
        token?.let { onSuccess(it) }
        onCancel()
    }

    private fun onError(error: Throwable?) = fragment.requireContext().run {
        Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT)
    }

    private fun onSuccess(token: OAuthToken) {
        com.tenutz.storemngsim.data.datasource.sharedpref.OAuthToken.save(
            accessToken = token.accessToken,
            refreshToken = token.refreshToken,
            socialType = SocialType.KAKAO.name,
        )

        UserApiClient.instance.me { user, error ->
            if(error != null) {
                viewModel.socialLogin(SocialProfileArgs(token.accessToken, SocialType.KAKAO))
            } else if(user != null) {
                viewModel.socialLogin(SocialProfileArgs(token.accessToken, SocialType.KAKAO, user.kakaoAccount?.profile?.nickname, user.kakaoAccount?.email, user.kakaoAccount?.profile?.thumbnailImageUrl))
            }
        }
    }

    private fun onCancel() {

    }
}