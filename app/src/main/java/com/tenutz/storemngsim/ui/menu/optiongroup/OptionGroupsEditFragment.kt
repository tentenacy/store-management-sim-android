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
import androidx.recyclerview.widget.ItemTouchHelper
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.data.datasource.api.dto.category.CategoriesDeleteRequest
import com.tenutz.storemngsim.data.datasource.api.dto.category.CategoryPrioritiesChangeRequest
import com.tenutz.storemngsim.data.datasource.api.dto.common.OptionGroupsDeleteRequest
import com.tenutz.storemngsim.databinding.*
import com.tenutz.storemngsim.ui.menu.category.main.MainCategoriesEditViewModel
import com.tenutz.storemngsim.ui.menu.category.middle.*
import com.tenutz.storemngsim.utils.ItemTouchHelperCallback
import com.tenutz.storemngsim.utils.OnDragListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OptionGroupsEditFragment: Fragment() {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.setOptionGroupsEdit(args.optionGroups)
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