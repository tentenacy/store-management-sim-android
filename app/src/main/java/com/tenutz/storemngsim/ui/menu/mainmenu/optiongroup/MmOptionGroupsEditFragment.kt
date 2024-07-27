package com.tenutz.storemngsim.ui.menu.mainmenu.optiongroup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.data.datasource.api.dto.common.OptionGroupPrioritiesChangeRequest
import com.tenutz.storemngsim.data.datasource.api.dto.common.OptionGroupsDeleteRequest
import com.tenutz.storemngsim.databinding.FragmentMmOptionGroupsEditBinding
import com.tenutz.storemngsim.ui.menu.category.main.MainCategoriesEditViewModel
import com.tenutz.storemngsim.ui.menu.mainmenu.optiongroup.base.NavMmOptionGroupFragment
import com.tenutz.storemngsim.utils.ItemTouchHelperCallback
import com.tenutz.storemngsim.utils.OnDragListener
import com.tenutz.storemngsim.utils.ext.mainActivity
import com.tenutz.storemngsim.utils.ext.navigateToMainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MmOptionGroupsEditFragment: NavMmOptionGroupFragment(), OnDragListener<MmOptionGroupsEditViewHolder> {

    private var _binding: FragmentMmOptionGroupsEditBinding? = null
    val binding: FragmentMmOptionGroupsEditBinding get() = _binding!!

    val vm: MmOptionGroupsEditViewModel by viewModels()

    private val pVm: MmOptionGroupsViewModel by navGraphViewModels(R.id.navigation_mm_option_group) {
        defaultViewModelProviderFactory
    }

    private val adapter: MmOptionGroupsEditAdapter by lazy {
        MmOptionGroupsEditAdapter(
            onCheckedChangeListener = {
                vm.updateCheckedItemCount()
            },
            onDragListener = this@MmOptionGroupsEditFragment,
        ).apply {
            setHasStableIds(true)
        }
    }

    private lateinit var itemTouchHelper: ItemTouchHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMmOptionGroupsEditBinding.inflate(inflater, container, false)

        val args: MmOptionGroupsFragmentArgs by navArgs()
        binding.args = args.mainMenu
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
        binding.recyclerMmOptionGroupsEdit.adapter = adapter
        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerMmOptionGroupsEdit)
    }

    private fun setOnClickListeners() {
        binding.imageMmOptionGroupsEditBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.imageMmOptionGroupsEditHome.setOnClickListener {
            mainActivity().navigateToMainFragment()
        }
        binding.imageMmOptionGroupsEditHamburger.setOnClickListener {
            mainActivity().binding.drawerMain.openDrawer(GravityCompat.END)
        }
        binding.btnMmOptionGroupsEditBottomContainer.setOnClickListener {
            vm.deleteMainMenuMappers(
                request = OptionGroupsDeleteRequest(
                    adapter.items.filter { it.checked }.mapNotNull { it.optionGroupCode }
                )
            ) { pVm.mmOptionGroups() }
        }

        binding.constraintMmOptionGroupsEditAllContainer.setOnClickListener {
            adapter.checkOrUncheckAll()
        }
    }

    private fun observeData() {
        vm.mmOptionGroupsEdit.observe(viewLifecycleOwner) {
            adapter.updateItems(it.mmOptionGroupsEdit)
        }

        vm.viewEvent.observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                when (it.first) {
                    MainCategoriesEditViewModel.EVENT_NAVIGATE_UP -> {
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

    override fun onDragStart(holder: MmOptionGroupsEditViewHolder) {
        itemTouchHelper.startDrag(holder)
    }

    override fun onDragOver() {
        Logger.i(adapter.items.toString())
        vm.changeMainMenuMapperPriorities(
            request = OptionGroupPrioritiesChangeRequest(
                adapter.items.mapIndexedNotNull { priority: Int, item ->
                    item.optionGroupCode?.let {
                        OptionGroupPrioritiesChangeRequest.OptionGroup(
                            item.optionGroupCode, priority
                        )
                    }
                }
            ),
        ) { pVm.mmOptionGroups() }
    }
}