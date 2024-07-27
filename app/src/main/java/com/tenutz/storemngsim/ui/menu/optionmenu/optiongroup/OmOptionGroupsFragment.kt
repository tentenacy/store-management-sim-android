package com.tenutz.storemngsim.ui.menu.optionmenu.optiongroup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.databinding.FragmentOmOptionGroupsBinding
import com.tenutz.storemngsim.ui.menu.optionmenu.optiongroup.base.NavOmOptionGroupFragment
import com.tenutz.storemngsim.utils.ext.mainActivity
import com.tenutz.storemngsim.utils.ext.navigateToMainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OmOptionGroupsFragment: NavOmOptionGroupFragment() {

    private var _binding: FragmentOmOptionGroupsBinding? = null
    val binding: FragmentOmOptionGroupsBinding get() = _binding!!

    private val args: OmOptionGroupsFragmentArgs by navArgs()

    val vm: OmOptionGroupsViewModel by navGraphViewModels(R.id.navigation_om_option_group) {
        defaultViewModelProviderFactory
    }

    private val adapter: OmOptionGroupsAdapter by lazy {
        OmOptionGroupsAdapter {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOmOptionGroupsBinding.inflate(inflater, container, false)

        binding.vm = vm
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observeData()
        setOnClickListeners()
    }

    private fun initViews() {
        binding.args = args.optionMenu
        binding.recyclerOmOptionGroups.adapter = adapter
    }

    private fun observeData() {
        vm.optionMenuMappers.observe(viewLifecycleOwner) {
            adapter.updateItems(it.optionMappers)
            binding.recyclerOmOptionGroups.scrollToPosition(0)
        }
        vm.viewEvent.observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                when (it.first) {
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.imageOmOptionGroupsBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.imageOmOptionGroupsHome.setOnClickListener {
            mainActivity().navigateToMainFragment()
        }
        binding.imageOmOptionGroupsHamburger.setOnClickListener {
            mainActivity().binding.drawerMain.openDrawer(GravityCompat.END)
        }
        binding.textOmOptionGroupsEdit.setOnClickListener {
            vm.optionMenuMappers.value?.let {
                findNavController().navigate(
                    OmOptionGroupsFragmentDirections.actionOmOptionGroupsFragmentToOmOptionGroupsEditFragment(args.optionMenu, it)
                )
            }
        }
        binding.fabOmOptionGroupsAdd.setOnClickListener {
            findNavController().navigate(OmOptionGroupsFragmentDirections.actionOmOptionGroupsFragmentToOmOptionGroupAddFragment(args.optionMenu))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}