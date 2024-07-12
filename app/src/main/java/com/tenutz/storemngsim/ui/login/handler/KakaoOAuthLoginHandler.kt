package com.tenutz.storemngsim.ui.login.handler

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kakao.sdk.auth.model.OAuthToken
import com.tenutz.storemngsim.ui.login.LoginViewModel
import com.tenutz.storemngsim.utils.type.SocialType

class KakaoOAuthLoginHandler(private val fragment: Fragment): (OAuthToken?, Throwable?) -> Unit {

    private val viewModel by lazy {
        ViewModelProvider(fragment).get(LoginViewModel::class.java)
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
        viewModel.socialLogin(SocialType.KAKAO)
    }

    private fun onCancel() {

    }
}