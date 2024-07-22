package com.tenutz.storemngsim.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tenutz.storemngsim.data.datasource.sharedpref.Settings
import com.tenutz.storemngsim.databinding.FragmentSettingsBinding
import com.tenutz.storemngsim.ui.base.BaseFragment
import com.tenutz.storemngsim.ui.signup.SignupFormFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment: BaseFragment() {

    private var _binding: FragmentSettingsBinding? = null
    val binding: FragmentSettingsBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setListeners()
        setOnClickListeners()
    }

    private fun setListeners() {
        binding.switchSettingsPush.setOnCheckedChangeListener { view, isChecked ->
            Settings.pushReceived = isChecked
        }
        binding.switchSettingsAutoLogin.setOnCheckedChangeListener { view, isChecked ->
            Settings.autoLoggedIn = isChecked
        }
    }

    private fun initViews() {
        binding.pushReceived = Settings.pushReceived
        binding.autoLoggedIn = Settings.autoLoggedIn
    }

    private fun setOnClickListeners() {
        binding.imageSettingsBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnSettingsTerms1.setOnClickListener {
            findNavController().navigate(
                SettingsFragmentDirections.actionSettingsFragmentToTermsFragment(
                    "개인정보 처리방침",
                    "1",
                    readOnly = true,
                )
            )
        }
        binding.btnSettingsTerms2.setOnClickListener {
            findNavController().navigate(
                SettingsFragmentDirections.actionSettingsFragmentToTermsFragment(
                    "이용약관",
                    "2",
                    readOnly = true,
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}