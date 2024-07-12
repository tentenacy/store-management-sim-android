package com.tenutz.storemngsim.ui.login.handler

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.tenutz.storemngsim.data.datasource.sharedpref.OAuthToken
import com.tenutz.storemngsim.ui.login.LoginViewModel
import com.tenutz.storemngsim.utils.type.SocialType

class FacebookOAuthLoginHandler(private val fragment: Fragment) : FacebookCallback<LoginResult> {

    private val viewModel by lazy {
        ViewModelProvider(fragment).get(LoginViewModel::class.java)
    }

    //로그인에 성공하면 LoginResult 매개변수에 새로운 AccessToken과 최근에 부여되거나 거부된 권한이 포함됩니다.
    //맞춤 설정할 수 있는 속성에는 LoginBehavior, DefaultAudience, ToolTipPopup.Style 및 LoginButton의 권한이 포함되어 있습니다.
    override fun onSuccess(result: LoginResult) {
        result?.let {
            OAuthToken.save(
                accessToken = it.accessToken.token,
                refreshToken = null,
                socialType = SocialType.FACEBOOK.name,
            )

            viewModel.socialLogin(SocialType.FACEBOOK)
        }
    }

    override fun onCancel() {
    }

    override fun onError(error: FacebookException) {
        fragment.requireContext().run {
            Toast.makeText(this, "error: $error", Toast.LENGTH_SHORT).show()
        }
    }
}