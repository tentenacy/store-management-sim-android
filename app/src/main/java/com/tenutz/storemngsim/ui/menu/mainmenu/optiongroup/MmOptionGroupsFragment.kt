package com.tenutz.storemngsim.ui.menu.mainmenu.optiongroup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.databinding.*
import com.tenutz.storemngsim.ui.menu.mainmenu.optiongroup.args.MmOptionGroupsNavArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MmOptionGroupsFragment: Fragment() {

    private var _binding: FragmentMmOptionGroupsBinding? = null
    val binding: FragmentMmOptionGroupsBinding get() = _binding!!

    private lateinit var args: MmOptionGroupsNavArgs

    val vm: MmOptionGroupsViewModel by navGraphViewModels(R.id.navigation_mm_option_group) {
        defaultViewModelProviderFactory
    }

    private val adapter: MmOptionGroupsAdapter by lazy {
        MmOptionGroupsAdapter {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        args = requireArguments().getParcelable("mainMenu")!!

        vm.mmOptionGroups(args.mainCategoryCode, args.middleCategoryCode, args.subCategoryCode, args.menuCode)
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
        binding.args = args
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
        binding.textMmOptionGroupsEdit.setOnClickListener {
            vm.mainMenuMappers.value?.let {
                findNavController().navigate(
                    MmOptionGroupsFragmentDirections.actionMmOptionGroupsFragmentToMmOptionGroupsEditFragment(
                        args,
                        it,
                    )
                )
            }
        }
        binding.fabMmOptionGroupsAdd.setOnClickListener {
            findNavController().navigate(MmOptionGroupsFragmentDirections.actionMmOptionGroupsFragmentToMmOptionGroupAddFragment(
                args.mainCategoryCode,
                args.middleCategoryCode,
                args.subCategoryCode,
                args.menuCode,
            ))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}