package com.tenutz.storemngsim.ui.settings.profile

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
import androidx.navigation.navGraphViewModels
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.application.AddressSearchActivity
import com.tenutz.storemngsim.databinding.FragmentProfileEditBinding
import com.tenutz.storemngsim.ui.base.BaseFragment
import com.tenutz.storemngsim.utils.MyToast
import com.tenutz.storemngsim.utils.ext.mainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileEditFragment: BaseFragment() {

    private var _binding: FragmentProfileEditBinding? = null
    val binding: FragmentProfileEditBinding get() = _binding!!

    val vm: ProfileEditViewModel by viewModels()

    val args: ProfileEditFragmentArgs by navArgs()

    private val requestPermissionLauncher: ActivityResultLauncher<String> = registerForActivityResult(
        RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission is granted. Continue the action or workflow in your
            // app.
            mainActivity().phoneNumber()?.let { vm.setPhoneNumber(it) }
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
                    vm.setAddress(it.data?.getStringExtra("roadAddress") ?: "", "")
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileEditBinding.inflate(inflater, container, false)

        binding.args = args.profile
        vm.mapToLiveData(args.profile)
        binding.vm = vm
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
        observeData()
    }

    private fun setOnClickListeners() {
        binding.imageProfileEditBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnProfileEditPhone.setOnClickListener {
            requestPhoneNumber()
        }
        binding.btnProfileEditSearch.setOnClickListener {
            startAddressSearchActivity.launch(
                Intent(
                    mainActivity(),
                    AddressSearchActivity::class.java
                )
            )
        }
        binding.textProfileEditAddress.setOnClickListener {
            startAddressSearchActivity.launch(
                Intent(
                    mainActivity(),
                    AddressSearchActivity::class.java
                )
            )
        }
        binding.btnProfileEdit.setOnClickListener {
            vm.updateProfile(args.profile) {
                findNavController().previousBackStackEntry?.savedStateHandle?.set("profile", null)
                findNavController().popBackStack()
//                pVm.profile()
//                findNavController().navigateUp()
            }
        }
    }

    private fun observeData() {
        vm.viewEvent.observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                when (it.first) {
                    ProfileEditViewModel.EVENT_TOAST -> {
                        val message: String = it.second as String
                        MyToast.create(mainActivity(), message, 80)?.show()
                    }
                }
            }
        }
    }

    private fun requestPhoneNumber() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (ContextCompat.checkSelfPermission(
                    mainActivity(),
                    Manifest.permission.READ_PHONE_NUMBERS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                mainActivity().phoneNumber()?.let { vm.setPhoneNumber(it) }
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
                mainActivity().phoneNumber()?.let { vm.setPhoneNumber(it) }
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_STATE)) {
                requestPermissionLauncher.launch(Manifest.permission.READ_PHONE_STATE)
            } else {
                mainActivity().showPermissionDialog("전화")
            }
        }
    }

}