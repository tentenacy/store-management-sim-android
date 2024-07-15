package com.tenutz.storemngsim.ui.menu.optiongroup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.OptionGroupCreateRequest
import com.tenutz.storemngsim.databinding.FragmentOptionGroupAddBinding
import com.tenutz.storemngsim.ui.menu.category.main.MainCategoriesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OptionGroupAddFragment: Fragment() {

    private var _binding: FragmentOptionGroupAddBinding? = null
    val binding: FragmentOptionGroupAddBinding get() = _binding!!

    val vm: OptionGroupAddViewModel by viewModels()

    val pVm: MainCategoriesViewModel by navGraphViewModels(R.id.navigation_option_group) {
        defaultViewModelProviderFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOptionGroupAddBinding.inflate(inflater, container, false)

        return binding.root
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
                    OptionGroupAddViewModel.EVENT_NAVIGATE_UP -> {
                        findNavController().navigateUp()
                    }
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.btnOptionGroupAddSave.setOnClickListener {
            vm.createOptionGroup(
                OptionGroupCreateRequest(
                    binding.editOptionGroupAddCode.text.toString(),
                    binding.editOptionGroupAddName.text.toString(),
                    binding.switchOptionGroupAddToggle.isChecked,
                    binding.switchOptionGroupAddRequired.isChecked,
                )
            ) {
                pVm.mainCategories()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}