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
import com.tenutz.storemngsim.data.datasource.api.dto.category.MainCategoryCreateRequest
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.OptionGroupCreateRequest
import com.tenutz.storemngsim.databinding.FragmentOptionGroupAddBinding
import com.tenutz.storemngsim.ui.menu.category.main.MainCategoriesViewModel
import com.tenutz.storemngsim.ui.menu.optionmenu.OptionMenuAddViewModel
import com.tenutz.storemngsim.utils.MyToast
import com.tenutz.storemngsim.utils.ext.mainActivity
import com.tenutz.storemngsim.utils.validation.Validator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OptionGroupAddFragment: Fragment() {

    private var _binding: FragmentOptionGroupAddBinding? = null
    val binding: FragmentOptionGroupAddBinding get() = _binding!!

    val vm: OptionGroupAddViewModel by viewModels()

    val pVm: OptionGroupsViewModel by navGraphViewModels(R.id.navigation_option_group) {
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
                    OptionGroupAddViewModel.EVENT_TOAST -> {
                        MyToast.create(mainActivity(), it.second as String, 80)?.show()
                    }
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.imageOptionGroupAddBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.imageOptionGroupAddHome.setOnClickListener {
            findNavController().navigate(R.id.action_global_mainFragment)
        }
        binding.btnOptionGroupAddSave.setOnClickListener {
            Validator.validate(
                onValidation = {
                    Validator.validateOptionGroupName(binding.editOptionGroupAddName.text.toString(), true)
                    Validator.validateOptionGroupCode(binding.editOptionGroupAddCode.text.toString(), true)
                },
                onSuccess = {
                    vm.createOptionGroup(
                        OptionGroupCreateRequest(
                            binding.editOptionGroupAddCode.text.toString(),
                            binding.editOptionGroupAddName.text.toString(),
                            binding.switchOptionGroupAddToggle.isChecked,
                            binding.switchOptionGroupAddRequired.isChecked,
                        )
                    ) {
                        pVm.optionGroups()
                    }
                },
                onFailure = { e ->
                    MyToast.create(mainActivity(), e.errorCode.message, 80)?.show()
                },
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}