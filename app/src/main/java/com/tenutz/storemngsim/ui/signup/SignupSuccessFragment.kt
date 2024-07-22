package com.tenutz.storemngsim.ui.signup

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.kakao.sdk.user.UserApiClient
import com.nhn.android.naverlogin.OAuthLogin
import com.tenutz.storemngsim.data.datasource.api.dto.store.StoreMainResponse
import com.tenutz.storemngsim.databinding.FragmentSignupFormBinding
import com.tenutz.storemngsim.databinding.FragmentSignupSuccessBinding
import com.tenutz.storemngsim.databinding.FragmentSignupSuccessBindingImpl
import com.tenutz.storemngsim.ui.base.BaseFragment
import com.tenutz.storemngsim.ui.login.handler.FacebookOAuthLoginHandler
import com.tenutz.storemngsim.ui.login.handler.GoogleOAuthLoginHandler
import com.tenutz.storemngsim.ui.login.handler.KakaoOAuthLoginHandler
import com.tenutz.storemngsim.ui.login.handler.NaverOAuthLoginHandler
import com.tenutz.storemngsim.utils.constant.SocialScopeConstant
import com.tenutz.storemngsim.utils.ext.mainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignupSuccessFragment: BaseFragment() {

    private var _binding: FragmentSignupSuccessBinding? = null
    val binding: FragmentSignupSuccessBinding get() = _binding!!

    val vm: SignupSuccessViewModel by viewModels()

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

        setOnClickListeners()
        observeData()
    }

    private fun observeData() {
        vm.viewEvent.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                when (it.first) {
                    SignupSuccessViewModel.EVENT_NAVIGATE_TO_MAIN -> {

                    }
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.btnSignupSuccessMainMain.setOnClickListener {
//            vm.eventNavigateToMain()
            findNavController().navigate(
                SignupSuccessFragmentDirections.actionSignupSuccessFragmentToMainFragment()
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}