package com.tenutz.storemngsim.ui.signup

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.kakao.sdk.user.UserApiClient
import com.nhn.android.naverlogin.OAuthLogin
import com.tenutz.storemngsim.databinding.FragmentSignupFormBinding
import com.tenutz.storemngsim.databinding.FragmentSignupSuccessBinding
import com.tenutz.storemngsim.databinding.FragmentSignupSuccessBindingImpl
import com.tenutz.storemngsim.ui.login.handler.FacebookOAuthLoginHandler
import com.tenutz.storemngsim.ui.login.handler.GoogleOAuthLoginHandler
import com.tenutz.storemngsim.ui.login.handler.KakaoOAuthLoginHandler
import com.tenutz.storemngsim.ui.login.handler.NaverOAuthLoginHandler
import com.tenutz.storemngsim.utils.constant.SocialScopeConstant
import com.tenutz.storemngsim.utils.ext.mainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignupSuccessFragment: Fragment() {

    private var _binding: FragmentSignupSuccessBinding? = null
    val binding: FragmentSignupSuccessBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSignupSuccessBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignupSuccessMainMain.setOnClickListener {
            findNavController().navigate(SignupSuccessFragmentDirections.actionSignupSuccessFragmentToMainFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}