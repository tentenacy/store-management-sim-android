package com.tenutz.storemngsim.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tenutz.storemngsim.databinding.FragmentSignupFormBinding
import com.tenutz.storemngsim.databinding.FragmentSignupSuccessBinding
import dagger.hilt.android.AndroidEntryPoint

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