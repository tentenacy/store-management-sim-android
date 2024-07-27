package com.tenutz.storemngsim.ui.menu.optionmenu.optiongroup

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
import com.tenutz.storemngsim.databinding.FragmentOmOptionGroupsEditBinding
import com.tenutz.storemngsim.ui.menu.optionmenu.optiongroup.base.NavOmOptionGroupFragment
import com.tenutz.storemngsim.utils.ItemTouchHelperCallback
import com.tenutz.storemngsim.utils.OnDragListener
import com.tenutz.storemngsim.utils.ext.mainActivity
import com.tenutz.storemngsim.utils.ext.navigateToMainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OmOptionGroupsEditFragment: NavOmOptionGroupFragment(), OnDragListener<OmOptionGroupsEditViewHolder> {

    private var _binding: FragmentOmOptionGroupsEditBinding? = null
    val binding: FragmentOmOptionGroupsEditBinding get() = _binding!!

    val vm: OmOptionGroupsEditViewModel by viewModels()

    private val omOptionGroupsVm: OmOptionGroupsViewModel by navGraphViewModels(R.id.navigation_om_option_group) {
        defaultViewModelProviderFactory
    }

    private val adapter: OmOptionGroupsEditAdapter by lazy {
        OmOptionGroupsEditAdapter(
            onCheckedChangeListener = {
                vm.updateCheckedItemCount()
            },
            onDragListener = this@OmOptionGroupsEditFragment,
        ).apply {
            setHasStableIds(true)
        }
    }

    private lateinit var itemTouchHelper: ItemTouchHelper

    private val args: OmOptionGroupsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOmOptionGroupsEditBinding.inflate(inflater, container, false)

        binding.args = args.optionMenu
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
        binding.recyclerOmOptionGroupsEdit.adapter = adapter
        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerOmOptionGroupsEdit)
    }

    private fun setOnClickListeners() {
        binding.imageOmOptionGroupsEditBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.imageOmOptionGroupsEditHome.setOnClickListener {
            mainActivity().navigateToMainFragment()
        }
        binding.imageOmOptionGroupsEditHamburger.setOnClickListener {
            mainActivity().binding.drawerMain.openDrawer(GravityCompat.END)
        }
        binding.btnOmOptionGroupsEditBottomContainer.setOnClickListener {
            vm.deleteOptionMenuMappers(
                OptionGroupsDeleteRequest(
                    adapter.items.filter { it.checked }.mapNotNull { it.optionGroupCode }
                )
            ) { omOptionGroupsVm.omOptionGroups() }
        }

        binding.constraintOmOptionGroupsEditAllContainer.setOnClickListener {
            adapter.checkOrUncheckAll()
        }
    }

    private fun observeData() {
        vm.omOptionGroupsEdit.observe(viewLifecycleOwner) {
            adapter.updateItems(it.omOptionGroupsEdit)
        }

        vm.viewEvent.observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                when (it.first) {
                    OmOptionGroupsEditViewModel.EVENT_NAVIGATE_UP -> {
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

    override fun onDragStart(holder: OmOptionGroupsEditViewHolder) {
        itemTouchHelper.startDrag(holder)
    }

    override fun onDragOver() {
        Logger.i(adapter.items.toString())
        vm.changeOptionMenuMapperPriorities(
            OptionGroupPrioritiesChangeRequest(
                adapter.items.mapIndexedNotNull { priority: Int, item ->
                    item.optionGroupCode?.let {
                        OptionGroupPrioritiesChangeRequest.OptionGroup(
                            item.optionGroupCode, priority
                        )
                    }
                }
            ),
        ) { omOptionGroupsVm.omOptionGroups() }
    }
}