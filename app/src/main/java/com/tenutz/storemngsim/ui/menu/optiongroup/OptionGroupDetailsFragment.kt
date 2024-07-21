package com.tenutz.storemngsim.ui.menu.optiongroup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.OptionGroupCreateRequest
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.OptionGroupUpdateRequest
import com.tenutz.storemngsim.databinding.FragmentOptionGroupDetailsBinding
import com.tenutz.storemngsim.ui.menu.category.main.MainCategoriesViewModel
import com.tenutz.storemngsim.utils.MyToast
import com.tenutz.storemngsim.utils.ext.mainActivity
import com.tenutz.storemngsim.utils.validation.Validator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OptionGroupDetailsFragment: Fragment() {

    private var _binding: FragmentOptionGroupDetailsBinding? = null
    val binding: FragmentOptionGroupDetailsBinding get() = _binding!!

    val args: OptionGroupDetailsFragmentArgs by navArgs()

    val vm: OptionGroupDetailsViewModel by viewModels()
    private val pVm: OptionGroupsViewModel by navGraphViewModels(R.id.navigation_option_group) {
        defaultViewModelProviderFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.optionGroup(args.optionGroupCode)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOptionGroupDetailsBinding.inflate(inflater, container, false)

        binding.vm = vm
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.imageOptionGroupDetailsBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.imageOptionGroupDetailsHome.setOnClickListener {
            findNavController().navigate(R.id.action_global_mainFragment)
        }
        binding.btnOptionGroupDetailsCancel.setOnClickListener {
            binding.vm = vm
            vm.switchToReadMode()
        }
        binding.btnOptionGroupDetailsSave.setOnClickListener {
            Validator.validate(
                onValidation = {
                    Validator.validateOptionGroupName(binding.editOptionGroupDetailsName.text.toString(), true)
                },
                onSuccess = {
                    vm.updateOptionGroup(
                        args.optionGroupCode,
                        OptionGroupUpdateRequest(
                            binding.editOptionGroupDetailsName.text.toString(),
                            binding.switchOptionGroupDetailsToggle.isChecked,
                            binding.switchOptionGroupDetailsRequired.isChecked,
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