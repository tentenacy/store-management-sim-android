package com.tenutz.storemngsim.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tenutz.storemngsim.databinding.FragmentSignupSuccessBinding
import com.tenutz.storemngsim.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

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