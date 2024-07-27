package com.tenutz.storemngsim.ui.login.handler

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginResult
import com.tenutz.storemngsim.data.datasource.sharedpref.OAuthToken
import com.tenutz.storemngsim.ui.base.Loginable
import com.tenutz.storemngsim.ui.login.LoginFragment
import com.tenutz.storemngsim.ui.login.LoginViewModel
import com.tenutz.storemngsim.ui.login.args.SocialProfileArgs
import com.tenutz.storemngsim.ui.signup.SignupFormViewModel
import com.tenutz.storemngsim.utils.type.SocialType

class FacebookOAuthLoginHandler(private val fragment: Fragment) : FacebookCallback<LoginResult> {

    private val viewModel: Loginable by lazy {
        ViewModelProvider(fragment).get(
            when(fragment) {
                is LoginFragment -> LoginViewModel::class.java
                else -> SignupFormViewModel::class.java
            }
        )
    }

    //로그인에 성공하면 LoginResult 매개변수에 새로운 AccessToken과 최근에 부여되거나 거부된 권한이 포함됩니다.
    //맞춤 설정할 수 있는 속성에는 LoginBehavior, DefaultAudience, ToolTipPopup.Style 및 LoginButton의 권한이 포함되어 있습니다.
    override fun onSuccess(result: LoginResult) {
        OAuthToken.save(
            accessToken = result.accessToken.token,
            refreshToken = null,
            socialType = SocialType.FACEBOOK.name,
        )

        val graphRequest = GraphRequest.newMeRequest(result.accessToken) { jsonObject, response ->
            viewModel.socialLogin(SocialProfileArgs(result.accessToken.token, SocialType.FACEBOOK, jsonObject?.getString("name"), jsonObject?.getString("email"), jsonObject?.getJSONObject("picture")?.getJSONObject("data")?.getString("url")))
        }
        val parameters = Bundle()
        parameters.putString("fields", "name,email,picture.width(180).height(180)")
        graphRequest.parameters = parameters
        graphRequest.executeAsync()
    }

    override fun onCancel() {
    }

    override fun onError(error: FacebookException) {
        fragment.requireContext().run {
            Toast.makeText(this, "error: $error", Toast.LENGTH_SHORT).show()
        }
    }
}