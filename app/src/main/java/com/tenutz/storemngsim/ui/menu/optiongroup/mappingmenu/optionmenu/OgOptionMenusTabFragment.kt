package com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.optionmenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.OptionGroupOptionMappersResponse
import com.tenutz.storemngsim.databinding.TabOgOptionMenusBinding
import com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.OgMappingMenusFragment
import com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.OgMappingMenusFragmentArgs
import com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.OgMappingMenusFragmentDirections
import com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.optionmenu.base.NavOgOptionMenuFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OgOptionMenusTabFragment: NavOgOptionMenuFragment() {

    private var _binding: TabOgOptionMenusBinding? = null
    val binding: TabOgOptionMenusBinding get() = _binding!!

    val vm: OgOptionMenusViewModel by navGraphViewModels(R.id.navigation_og_mapping_menu) {
        defaultViewModelProviderFactory
    }

    private val args: OgMappingMenusFragmentArgs by lazy {
        (requireParentFragment() as OgMappingMenusFragment).args
    }

    private val adapter: OgOptionMenusAdapter by lazy {
        OgOptionMenusAdapter {

        }.apply {
            setHasStableIds(true)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = TabOgOptionMenusBinding.inflate(inflater, container, false)

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
        binding.recyclerTogOptionMenus.adapter = adapter
    }

    private fun observeData() {
        vm.ogOptionMenuMappers.observe(viewLifecycleOwner) {

            if(vm.hideRemoval.value == true) {
                adapter.updateItems(it.optionGroupOptionMappers.filter { it.use != null })
            } else {
                adapter.updateItems(it.optionGroupOptionMappers)
            }
            binding.recyclerTogOptionMenus.scrollToPosition(0)
        }
        vm.viewEvent.observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                when (it.first) {
                    OgOptionMenusViewModel.EVENT_HIDE_REMOVAL -> {
                        adapter.updateItems(vm.ogOptionMenuMappers.value?.optionGroupOptionMappers?.filter { it.use != null })
                    }
                    OgOptionMenusViewModel.EVENT_SHOW_REMOVAL -> {
                        adapter.updateItems(vm.ogOptionMenuMappers.value?.optionGroupOptionMappers)
                    }
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.textTogOptionMenusEdit.setOnClickListener {
            vm.ogOptionMenuMappers.value?.let {
                findNavController().navigate(
                    OgMappingMenusFragmentDirections.actionOgMappingMenusFragmentToOgOptionMenusEditFragment(
                        OptionGroupOptionMappersResponse(it.optionGroupOptionMappers.filter { it.use != null }),
                        args.optionGroup,
                    )
                )
            }
        }
        binding.textTogOptionMenusShowHideRemoval.setOnClickListener {
            if(vm.hideRemoval.value == true) {
                vm.showRemoval()
            } else {
                vm.hideRemoval()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}