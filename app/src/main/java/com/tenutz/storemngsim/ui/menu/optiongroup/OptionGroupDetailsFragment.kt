package com.tenutz.storemngsim.ui.menu.optiongroup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.OptionGroupUpdateRequest
import com.tenutz.storemngsim.databinding.FragmentOptionGroupDetailsBinding
import com.tenutz.storemngsim.ui.menu.optiongroup.base.NavOptionGroupFragment
import com.tenutz.storemngsim.utils.MyToast
import com.tenutz.storemngsim.utils.ext.mainActivity
import com.tenutz.storemngsim.utils.ext.navigateToMainFragment
import com.tenutz.storemngsim.utils.validation.Validator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OptionGroupDetailsFragment: NavOptionGroupFragment() {

    private var _binding: FragmentOptionGroupDetailsBinding? = null
    val binding: FragmentOptionGroupDetailsBinding get() = _binding!!

    val args: OptionGroupDetailsFragmentArgs by navArgs()

    val vm: OptionGroupDetailsViewModel by viewModels()

    private val pVm: OptionGroupsViewModel by navGraphViewModels(R.id.navigation_option_group) {
        defaultViewModelProviderFactory
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
            mainActivity().navigateToMainFragment()
        }
        binding.imageOptionGroupDetailsHamburger.setOnClickListener {
            mainActivity().binding.drawerMain.openDrawer(GravityCompat.END)
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