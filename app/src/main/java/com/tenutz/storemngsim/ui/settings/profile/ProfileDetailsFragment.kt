package com.tenutz.storemngsim.ui.settings.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.databinding.FragmentProfileDetailsBinding
import com.tenutz.storemngsim.ui.base.BaseFragment
import com.tenutz.storemngsim.ui.settings.profile.args.ProfileArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileDetailsFragment: BaseFragment() {

    private var _binding: FragmentProfileDetailsBinding? = null
    val binding: FragmentProfileDetailsBinding get() = _binding!!

    private val vm: ProfileDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileDetailsBinding.inflate(inflater, container, false)

        binding.vm = vm
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
        observeData()
    }

    private fun observeData() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("profile")?.observe(viewLifecycleOwner) { _ ->
            vm.profile()
        }
    }

    private fun setOnClickListeners() {

        binding.imageProfileDetailsBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnProfileDetailsEdit.setOnClickListener {
            vm.details.value?.let {
                findNavController().navigate(
                    ProfileDetailsFragmentDirections.actionProfileDetailsFragmentToProfileEditFragment(
                        ProfileArgs.of(it)
                    )
                )
            }
        }
    }


}