package com.tenutz.storemngsim.ui.login.handler

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import com.tenutz.storemngsim.data.datasource.sharedpref.OAuthToken
import com.tenutz.storemngsim.ui.base.Loginable
import com.tenutz.storemngsim.ui.login.LoginFragment
import com.tenutz.storemngsim.ui.login.LoginViewModel
import com.tenutz.storemngsim.ui.login.args.SocialProfileArgs
import com.tenutz.storemngsim.ui.signup.SignupFormViewModel
import com.tenutz.storemngsim.utils.type.SocialType

class NaverOAuthLoginCallback(
    private val fragment: Fragment
) : OAuthLoginCallback {

    private val viewModel: Loginable by lazy {
        ViewModelProvider(fragment).get(
            when(fragment) {
                is LoginFragment -> LoginViewModel::class.java
                else -> SignupFormViewModel::class.java
            }
        )
    }

    override fun onSuccess(): Unit = fragment.requireContext().run {
        OAuthToken.save(
            accessToken = NaverIdLoginSDK.getAccessToken()!!,
            refreshToken = NaverIdLoginSDK.getRefreshToken(),
            socialType = SocialType.NAVER.name,
        )

        NidOAuthLogin().callProfileApi(object : NidProfileCallback<NidProfileResponse> {
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }

            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                Toast.makeText(this@run,"errorCode:$errorCode, errorDesc:$errorDescription",Toast.LENGTH_SHORT).show()

                viewModel.socialLogin(SocialProfileArgs(NaverIdLoginSDK.getAccessToken()!!, SocialType.NAVER))
            }

            override fun onSuccess(result: NidProfileResponse) {
                viewModel.socialLogin(SocialProfileArgs(NaverIdLoginSDK.getAccessToken()!!, SocialType.NAVER, result.profile?.name, result.profile?.email, result.profile?.profileImage))
            }
        })
    }

    override fun onError(errorCode: Int, message: String) {
        onFailure(errorCode, message)
    }

    override fun onFailure(httpStatus: Int, message: String) = fragment.requireContext().run {
        val errorCode = NaverIdLoginSDK.getLastErrorCode().code
        val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
        Toast.makeText(this,"errorCode:$errorCode, errorDesc:$errorDescription",Toast.LENGTH_SHORT).show()
    }
}