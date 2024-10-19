package com.tenutz.storemngsim.ui.signup

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.tenutz.storemngsim.application.AddressSearchActivity
import com.tenutz.storemngsim.data.datasource.api.dto.user.SocialSignupRequest
import com.tenutz.storemngsim.databinding.FragmentSignupFormBinding
import com.tenutz.storemngsim.ui.base.BaseFragment
import com.tenutz.storemngsim.ui.login.handler.FacebookOAuthLoginHandler
import com.tenutz.storemngsim.ui.login.handler.GoogleOAuthLoginHandler
import com.tenutz.storemngsim.ui.login.handler.KakaoOAuthLoginHandler
import com.tenutz.storemngsim.ui.login.handler.NaverOAuthLoginCallback
import com.tenutz.storemngsim.utils.MyToast
import com.tenutz.storemngsim.utils.constant.SocialScopeConstant
import com.tenutz.storemngsim.utils.ext.getNavigationResult
import com.tenutz.storemngsim.utils.ext.mainActivity
import com.tenutz.storemngsim.utils.ext.randomNumber
import com.tenutz.storemngsim.utils.ext.toBusinessNumber
import com.tenutz.storemngsim.utils.type.SocialType
import com.tenutz.storemngsim.utils.validation.Validator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SignupFormFragment : BaseFragment() {

    private var _binding: FragmentSignupFormBinding? = null
    val binding: FragmentSignupFormBinding get() = _binding!!

    val args: SignupFormFragmentArgs by navArgs()

    val vm: SignupFormViewModel by viewModels()

    private val requestPermissionLauncher: ActivityResultLauncher<String> = registerForActivityResult(
        RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission is granted. Continue the action or workflow in your
            // app.
            mainActivity().phoneNumber()?.let { vm.phoneNumber.value = it }
        } else {
            // Explain to the user that the feature is unavailable because the
            // features requires a permission that the user has denied. At the
            // same time, respect the user's decision. Don't link to system
            // settings in an effort to convince the user to change their
            // decision.
            MyToast.create(mainActivity(), "전화번호가 없거나 권한이 없어 전화번호를 가져올 수 없습니다.", 80)?.show()
        }
    }

    private val startAddressSearchActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            when (it.resultCode) {
                AppCompatActivity.RESULT_OK -> {
//                    it.data?.getStringExtra("address") ?: ""
                    binding.textSignupFormAddress.text = it.data?.getStringExtra("roadAddress") ?: ""
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSignupFormBinding.inflate(inflater, container, false)

        binding.args = args.socialProfile
        binding.vm = vm
        binding.lifecycleOwner = this

        requestPhoneNumber()

        return binding.root
    }

    private fun requestPhoneNumber() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (ContextCompat.checkSelfPermission(
                    mainActivity(),
                    Manifest.permission.READ_PHONE_NUMBERS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                mainActivity().phoneNumber()?.let { vm.phoneNumber.value = it }
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_NUMBERS)) {
                // In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected. In this UI,
                // include a "cancel" or "no thanks" button that allows the user to
                // continue using your app without granting the permission.
                requestPermissionLauncher.launch(Manifest.permission.READ_PHONE_NUMBERS)
            } else {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                mainActivity().showPermissionDialog("전화")
            }
        } else {
            if (ContextCompat.checkSelfPermission(
                    mainActivity(),
                    Manifest.permission.READ_PHONE_STATE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                mainActivity().phoneNumber()?.let { vm.phoneNumber.value = it }
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_STATE)) {
                requestPermissionLauncher.launch(Manifest.permission.READ_PHONE_STATE)
            } else {
                mainActivity().showPermissionDialog("전화")
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
        observeData()
    }

    private fun observeData() {
        vm.viewEvent.observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                when (it.first) {
                    SignupFormViewModel.EVENT_TOAST -> {
                        MyToast.create(mainActivity(), it.second as String, 80)?.show()
                    }
                    SignupFormViewModel.EVENT_NAVIGATE_TO_SIGNUP_SUCCESS -> {
                        findNavController().navigate(SignupFormFragmentDirections.actionSignupFormFragmentToSignupSuccessFragment())
                    }
                    SignupFormViewModel.EVENT_SOCIAL_LOGIN -> {
                        (it.second as SocialType?)?.let {
                            vm.socialLogin(args.socialProfile)
                        }
                        /*(it.second as SocialType?)?.let {
                            when(it) {
                                SocialType.KAKAO -> kakaoLoginOnClickListener()
                                SocialType.GOOGLE -> googleLoginOnClickListener()
                                SocialType.FACEBOOK -> facebookLoginOnClickListener()
                                SocialType.NAVER -> naverLoginOnClickListener()
                            }
                        }*/
                    }
                }
            }
        }
        getNavigationResult<Pair<String, Boolean>>("agree")?.observe(viewLifecycleOwner) {
            when (it.first) {
                "1" -> {
                    vm.checkTerms1()
                }
                "2" -> {
                    vm.checkTerms2()
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.btnSignupFormPhone.setOnClickListener {
            requestPhoneNumber()
        }
        binding.btnSignupFormSearch.setOnClickListener {
            startAddressSearchActivity.launch(
                Intent(
                    mainActivity(),
                    AddressSearchActivity::class.java
                )
            )
        }
        binding.textSignupFormAddress.setOnClickListener {
            startAddressSearchActivity.launch(
                Intent(
                    mainActivity(),
                    AddressSearchActivity::class.java
                )
            )
        }
        binding.btnSignupFormSignup.setOnClickListener {
            Validator.validate(
                onValidation = {
                    Validator.validateBusinessNumber(binding.editSignupFormBizNo.text.toString().replace("-", ""), true)
                    Validator.validateRepresentative(binding.editSignupFormOwnerName.text.toString())
                    Validator.validatePhoneNumber(binding.editSignupFormPhone.text.toString(), true)
                    Validator.validateStoreName(binding.editSignupFormName.text.toString(), true)
                    Validator.validateAddress(binding.textSignupFormAddress.text.toString(), binding.editSignupFormAddressDetail.text.toString())
                },
                onSuccess = {
                    vm.signup(
                        args.socialProfile.socialType.name,
                        SocialSignupRequest(
                            args.socialProfile.accessToken,
                            binding.editSignupFormBizNo.text.toString().replace("-", ""),
                            binding.editSignupFormPhone.text.toString(),
                            binding.editSignupFormOwnerName.text.toString(),
                            binding.editSignupFormName.text.toString(),
                            "${binding.textSignupFormAddress.text}|${binding.editSignupFormAddressDetail.text}".trim(),
                        )
                    )
                },
                onFailure = { e ->
                    MyToast.create(mainActivity(), e.errorCode.message, 80)?.show()
                },
            )
        }
        binding.btnSignupFormAutoCreate.setOnClickListener {
            binding.editSignupFormBizNo.setText(randomNumber(10).toBusinessNumber)

        }
        binding.textSignupFormTerms1Details.setOnClickListener {
            findNavController().navigate(
                SignupFormFragmentDirections.actionSignupFormFragmentToTermsFragment(
                    "개인정보 처리방침",
                    "1",
                )
            )
        }
        binding.textSignupFormTerms2Details.setOnClickListener {
            findNavController().navigate(
                SignupFormFragmentDirections.actionSignupFormFragmentToTermsFragment(
                    "이용약관",
                    "2",
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}