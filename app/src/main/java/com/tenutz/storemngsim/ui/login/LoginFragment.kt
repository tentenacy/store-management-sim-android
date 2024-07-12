package com.tenutz.storemngsim.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.kakao.sdk.user.UserApiClient
import com.nhn.android.naverlogin.OAuthLogin
import com.tenutz.storemngsim.databinding.FragmentLoginBinding
import com.tenutz.storemngsim.ui.login.handler.FacebookOAuthLoginHandler
import com.tenutz.storemngsim.ui.login.handler.GoogleOAuthLoginHandler
import com.tenutz.storemngsim.ui.login.handler.KakaoOAuthLoginHandler
import com.tenutz.storemngsim.ui.login.handler.NaverOAuthLoginHandler
import com.tenutz.storemngsim.utils.constant.SocialScopeConstant
import com.tenutz.storemngsim.utils.ext.mainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment: Fragment() {

    private var _binding: FragmentLoginBinding? = null
    val binding: FragmentLoginBinding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    @Inject
    lateinit var naverOAuthLoginHandler: NaverOAuthLoginHandler

    @Inject
    lateinit var facebookOAuthLoginHandler: FacebookOAuthLoginHandler

    @Inject
    lateinit var kakaoOAuthLoginHandler: KakaoOAuthLoginHandler

    @Inject
    lateinit var googleOAuthLoginHandler: GoogleOAuthLoginHandler



    @Inject
    lateinit var googleCallbackManager: GoogleSignInClient

    @Inject
    lateinit var facebookCallbackManager: CallbackManager

    @Inject
    lateinit var naverCallbackManager: OAuthLogin



    private val naverLoginOnClickListener: (View?) -> Unit = {
        naverCallbackManager.startOauthLoginActivity(
            mainActivity(),
            naverOAuthLoginHandler
        )
    }

    private val kakaoLoginOnClickListener: (View?) -> Unit = {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
            UserApiClient.instance.loginWithKakaoTalk(
                requireContext(),
                callback = kakaoOAuthLoginHandler
            )
        } else {
            UserApiClient.instance.loginWithKakaoAccount(
                requireContext(),
                callback = kakaoOAuthLoginHandler
            )
        }
    }

    private val googleLoginOnClickListener: (View?) -> Unit = {
        mainActivity().activityResultFactory.launch(
            googleCallbackManager.signInIntent,
            googleOAuthLoginHandler
        )
    }

    private val facebookLoginOnClickListener: (View?) -> Unit = {
        LoginManager.getInstance().run {
            logInWithReadPermissions(
                this@LoginFragment,
                listOf(
                    SocialScopeConstant.FACEBOOK_SCOPE_EMAIL,
                    SocialScopeConstant.FACEBOOK_SCOPE_PUBLIC_PROFILE
                )
            )
            registerCallback(facebookCallbackManager, facebookOAuthLoginHandler)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
        observeData()
    }

    private fun observeData() {
        viewModel.viewEvent.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let {
                when (it.first) {
                    LoginViewModel.EVENT_NAVIGATE_TO_MAIN -> {
                        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMainFragment())
                    }
                    LoginViewModel.EVENT_NAVIGATE_TO_SIGNUP -> {
                        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignupFormFragment())
                    }
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.imageLoginKakao.setOnClickListener(kakaoLoginOnClickListener)
        binding.imageLoginGoogle.setOnClickListener(googleLoginOnClickListener)
        binding.imageLoginFacebook.setOnClickListener(facebookLoginOnClickListener)
        binding.imageLoginNaver.setOnClickListener(naverLoginOnClickListener)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        facebookCallbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}