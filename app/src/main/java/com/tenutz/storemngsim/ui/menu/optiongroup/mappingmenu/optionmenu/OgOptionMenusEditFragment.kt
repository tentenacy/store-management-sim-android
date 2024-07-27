package com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.optionmenu

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
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.OptionGroupOptionMapperPrioritiesChangeRequest
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.OptionGroupOptionMappersDeleteRequest
import com.tenutz.storemngsim.databinding.FragmentOgOptionMenusEditBinding
import com.tenutz.storemngsim.ui.menu.category.main.MainCategoriesEditViewModel
import com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.OgMappingMenusFragmentArgs
import com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.optionmenu.base.NavOgOptionMenuFragment
import com.tenutz.storemngsim.utils.ItemTouchHelperCallback
import com.tenutz.storemngsim.utils.OnDragListener
import com.tenutz.storemngsim.utils.ext.mainActivity
import com.tenutz.storemngsim.utils.ext.navigateToMainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OgOptionMenusEditFragment: NavOgOptionMenuFragment(), OnDragListener<OgOptionMenusEditViewHolder> {

    private var _binding: FragmentOgOptionMenusEditBinding? = null
    val binding: FragmentOgOptionMenusEditBinding get() = _binding!!

    val args: OgOptionMenusEditFragmentArgs by navArgs()

    val vm: OgOptionMenusEditViewModel by viewModels()

    private val ogOptionMenusVm: OgOptionMenusViewModel by navGraphViewModels(R.id.navigation_og_mapping_menu) {
        defaultViewModelProviderFactory
    }

    private val adapter: OgOptionMenusEditAdapter by lazy {
        OgOptionMenusEditAdapter(
            onCheckedChangeListener = {
                vm.updateCheckedItemCount()
            },
            onDragListener = this@OgOptionMenusEditFragment,
        ).apply {
            setHasStableIds(true)
        }
    }

    private lateinit var itemTouchHelper: ItemTouchHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOgOptionMenusEditBinding.inflate(inflater, container, false)

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
        val args: OgMappingMenusFragmentArgs by navArgs()
        binding.args = args.optionGroup
        binding.recyclerOgOptionMenusEdit.adapter = adapter
        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerOgOptionMenusEdit)
    }

    private fun setOnClickListeners() {
        binding.imageOgOptionMenusEditBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.imageOgOptionMenusEditHome.setOnClickListener {
            mainActivity().navigateToMainFragment()
        }
        binding.imageOgOptionMenusEditHamburger.setOnClickListener {
            mainActivity().binding.drawerMain.openDrawer(GravityCompat.END)
        }
        binding.btnOgOptionMenusEditBottomContainer.setOnClickListener {
            vm.deleteOgOptionMenus(
                OptionGroupOptionMappersDeleteRequest(
                    adapter.items.filter { it.checked }.mapNotNull {
                        it.optionCode
                    }
                )
            ) { ogOptionMenusVm.ogOptionMenuMappers() }
        }

        binding.constraintOgOptionMenusEditAllContainer.setOnClickListener {
            adapter.checkOrUncheckAll()
        }
    }

    private fun observeData() {
        vm.ogOptionMenusEdit.observe(viewLifecycleOwner) {
            adapter.updateItems(it.ogOptionMenuMappersEdit)
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

    override fun onDragStart(holder: OgOptionMenusEditViewHolder) {
        itemTouchHelper.startDrag(holder)
    }

    override fun onDragOver() {
        Logger.i(adapter.items.toString())
        vm.changeMiddleCategoryPriorities(
            OptionGroupOptionMapperPrioritiesChangeRequest(
                adapter.items.mapIndexedNotNull { priority: Int, item ->
                    item.optionCode?.let {
                        OptionGroupOptionMapperPrioritiesChangeRequest.OptionGroupOption(
                            it,
                            priority,
                        )
                    }
                }
            ),
        ) { ogOptionMenusVm.ogOptionMenuMappers() }
    }
}