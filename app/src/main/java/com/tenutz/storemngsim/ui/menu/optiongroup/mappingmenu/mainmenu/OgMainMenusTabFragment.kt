package com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.mainmenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.OptionGroupMainMenuMappersResponse
import com.tenutz.storemngsim.databinding.TabOgMainMenusBinding
import com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.OgMappingMenusFragment
import com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.OgMappingMenusFragmentArgs
import com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.OgMappingMenusFragmentDirections
import com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.mainmenu.base.NavOgMainMenuFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OgMainMenusTabFragment: NavOgMainMenuFragment() {

    private var _binding: TabOgMainMenusBinding? = null
    val binding: TabOgMainMenusBinding get() = _binding!!

    val vm: OgMainMenusViewModel by navGraphViewModels(R.id.navigation_og_mapping_menu) {
        defaultViewModelProviderFactory
    }

    private val args: OgMappingMenusFragmentArgs by lazy {
        (requireParentFragment() as OgMappingMenusFragment).args
    }

    private val adapter: OgMainMenusAdapter by lazy {
        OgMainMenusAdapter {

        }.apply {
            setHasStableIds(true)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = TabOgMainMenusBinding.inflate(inflater, container, false)

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
        binding.recyclerTogMainMenus.adapter = adapter
    }

    private fun observeData() {

        vm.ogMainMenuMappers.observe(viewLifecycleOwner) {

            if(vm.hideRemoval.value == true) {
                adapter.updateItems(it.optionGroupMainMenuMappers.filter { it.use != null })
            } else {
                adapter.updateItems(it.optionGroupMainMenuMappers)
            }
            binding.recyclerTogMainMenus.scrollToPosition(0)
        }

        vm.viewEvent.observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                when (it.first) {
                    OgMainMenusViewModel.EVENT_HIDE_REMOVAL -> {
                        adapter.updateItems(vm.ogMainMenuMappers.value?.optionGroupMainMenuMappers?.filter { it.use != null })
                    }
                    OgMainMenusViewModel.EVENT_SHOW_REMOVAL -> {
                        adapter.updateItems(vm.ogMainMenuMappers.value?.optionGroupMainMenuMappers)
                    }
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.textTogMainMenusEdit.setOnClickListener {
            vm.ogMainMenuMappers.value?.let {
                findNavController().navigate(
                    OgMappingMenusFragmentDirections.actionOgMappingMenusFragmentToOgMainMenusEditFragment(
                        OptionGroupMainMenuMappersResponse((it.optionGroupMainMenuMappers.filter { it.use != null })),
                        args.optionGroup
                    )
                )
            }
        }
        binding.textTogMainMenusShowHideRemoval.setOnClickListener {
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