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
import com.tenutz.storemngsim.data.datasource.api.dto.common.OptionGroupsDeleteRequest
import com.tenutz.storemngsim.databinding.FragmentOptionGroupsEditBinding
import com.tenutz.storemngsim.ui.menu.optiongroup.base.NavOptionGroupFragment
import com.tenutz.storemngsim.utils.ext.mainActivity
import com.tenutz.storemngsim.utils.ext.navigateToMainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OptionGroupsEditFragment: NavOptionGroupFragment() {

    private var _binding: FragmentOptionGroupsEditBinding? = null
    val binding: FragmentOptionGroupsEditBinding get() = _binding!!

    val args: OptionGroupsEditFragmentArgs by navArgs()

    val vm: OptionGroupsEditViewModel by viewModels()

    private val pVm: OptionGroupsViewModel by navGraphViewModels(R.id.navigation_option_group) {
        defaultViewModelProviderFactory
    }

    private val adapter: OptionGroupsEditAdapter by lazy {
        OptionGroupsEditAdapter(
            onCheckedChangeListener = {
                vm.updateCheckedItemCount()
            },
        ).apply {
            setHasStableIds(true)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOptionGroupsEditBinding.inflate(inflater, container, false)

        binding.vm = vm
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setOnClickListeners()
        observeData()
    }

    private fun initViews() {
        binding.recyclerOptionGroupsEdit.adapter = adapter
    }

    private fun setOnClickListeners() {
        binding.imageOptionGroupsEditBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.imageOptionGroupsEditHome.setOnClickListener {
            mainActivity().navigateToMainFragment()
        }
        binding.imageOptionGroupsEditHamburger.setOnClickListener {
            mainActivity().binding.drawerMain.openDrawer(GravityCompat.END)
        }
        binding.btnOptionGroupsEditBottomContainer.setOnClickListener {
            vm.deleteOptionGroups(
                OptionGroupsDeleteRequest(
                    adapter.items.filter { it.checked }.mapNotNull { it.optionGroupCode }
                )
            ) {
                pVm.optionGroups()
            }
        }

        binding.constraintOptionGroupsEditAllContainer.setOnClickListener {
            adapter.checkOrUncheckAll()
        }
    }

    private fun observeData() {

        vm.optionGroupsEdit.observe(viewLifecycleOwner) {
            adapter.updateItems(it.optionGroupsEdit)
        }

        vm.viewEvent.observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                when (it.first) {
                    OptionGroupsEditViewModel.EVENT_NAVIGATE_UP -> {
                        findNavController().navigateUp()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}