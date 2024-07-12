package com.tenutz.storemngsim.ui.login.handler

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import com.tenutz.storemngsim.data.datasource.sharedpref.OAuthToken
import com.tenutz.storemngsim.ui.login.LoginViewModel
import com.tenutz.storemngsim.utils.type.SocialType

class NaverOAuthLoginHandler(
    private val fragment: Fragment,
    private val naverLoginManager: OAuthLogin
) : OAuthLoginHandler() {

    private val viewModel by lazy {
        ViewModelProvider(fragment).get(LoginViewModel::class.java)
    }

    override fun run(success: Boolean): Unit = fragment.requireContext().run {
        if (success) {
            onSuccess()
        } else {
            onError()
        }
        onCancel()
    }

    private fun onSuccess() = fragment.requireContext().run {
        OAuthToken.save(
            accessToken = naverLoginManager.getAccessToken(
                this
            ),
            refreshToken = naverLoginManager.getRefreshToken(
                this
            ),
            socialType = SocialType.NAVER.name,
        )

        viewModel.socialLogin(SocialType.NAVER)
    }

    private fun onError() = fragment.requireContext().run {
        Toast.makeText(
            this,
            "errorCode: ${
                naverLoginManager.getLastErrorCode(
                    this
                )
            }, errorDesc: ${
                naverLoginManager.getLastErrorDesc(this)
            }",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun onCancel() {

    }
}