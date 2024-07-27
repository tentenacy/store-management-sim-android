package com.tenutz.storemngsim.ui.menu.mainmenu.optiongroup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.databinding.FragmentMmOptionGroupsBinding
import com.tenutz.storemngsim.ui.menu.mainmenu.optiongroup.base.NavMmOptionGroupFragment
import com.tenutz.storemngsim.utils.ext.mainActivity
import com.tenutz.storemngsim.utils.ext.navigateToMainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MmOptionGroupsFragment: NavMmOptionGroupFragment() {

    private var _binding: FragmentMmOptionGroupsBinding? = null
    val binding: FragmentMmOptionGroupsBinding get() = _binding!!

    private val args: MmOptionGroupsFragmentArgs by navArgs()

    val vm: MmOptionGroupsViewModel by navGraphViewModels(R.id.navigation_mm_option_group) {
        defaultViewModelProviderFactory
    }

    private val adapter: MmOptionGroupsAdapter by lazy {
        MmOptionGroupsAdapter {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMmOptionGroupsBinding.inflate(inflater, container, false)

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
        binding.args = args.mainMenu
        binding.recyclerMmOptionGroups.adapter = adapter
    }

    private fun observeData() {
        vm.mainMenuMappers.observe(viewLifecycleOwner) {
            adapter.updateItems(it.mainMenuMappers)
            binding.recyclerMmOptionGroups.scrollToPosition(0)
        }
        vm.viewEvent.observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                when (it.first) {
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.imageMmOptionGroupsBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.imageMmOptionGroupsHome.setOnClickListener {
            mainActivity().navigateToMainFragment()
        }
        binding.imageMmOptionGroupsHamburger.setOnClickListener {
            mainActivity().binding.drawerMain.openDrawer(GravityCompat.END)
        }
        binding.textMmOptionGroupsEdit.setOnClickListener {
            vm.mainMenuMappers.value?.let {
                findNavController().navigate(
                    MmOptionGroupsFragmentDirections.actionMmOptionGroupsFragmentToMmOptionGroupsEditFragment(args.subCategory, args.mainMenu)
                )
            }
        }
        binding.fabMmOptionGroupsAdd.setOnClickListener {
            findNavController().navigate(MmOptionGroupsFragmentDirections.actionMmOptionGroupsFragmentToMmOptionGroupAddFragment(args.subCategory, args.mainMenu))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}