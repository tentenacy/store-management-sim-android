package com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.mainmenu

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
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.OptionGroupMainMenuMapperPrioritiesChangeRequest
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.OptionGroupMainMenuMappersDeleteRequest
import com.tenutz.storemngsim.databinding.FragmentOgMainMenusEditBinding
import com.tenutz.storemngsim.ui.menu.category.main.MainCategoriesEditViewModel
import com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.OgMappingMenusFragmentArgs
import com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.OgMappingMenusViewModel
import com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.mainmenu.base.NavOgMainMenuFragment
import com.tenutz.storemngsim.utils.ItemTouchHelperCallback
import com.tenutz.storemngsim.utils.OnDragListener
import com.tenutz.storemngsim.utils.ext.mainActivity
import com.tenutz.storemngsim.utils.ext.navigateToMainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OgMainMenusEditFragment: NavOgMainMenuFragment(), OnDragListener<OgMainMenusEditViewHolder> {

    private var _binding: FragmentOgMainMenusEditBinding? = null
    val binding: FragmentOgMainMenusEditBinding get() = _binding!!

    val vm: OgMainMenusEditViewModel by viewModels()

    private val ogMainMenusVm: OgMainMenusViewModel by navGraphViewModels(R.id.navigation_og_mapping_menu) {
        defaultViewModelProviderFactory
    }

    private val adapter: OgMainMenusEditAdapter by lazy {
        OgMainMenusEditAdapter(
            onCheckedChangeListener = {
                vm.updateCheckedItemCount()
            },
            onDragListener = this@OgMainMenusEditFragment,
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

        _binding = FragmentOgMainMenusEditBinding.inflate(inflater, container, false)

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
        binding.recyclerOgMainMenusEdit.adapter = adapter
        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerOgMainMenusEdit)
    }

    private fun setOnClickListeners() {
        binding.imageOgMainMenusEditBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.imageOgMainMenusEditHome.setOnClickListener {
            mainActivity().navigateToMainFragment()
        }
        binding.imageOgMainMenusEditHamburger.setOnClickListener {
            mainActivity().binding.drawerMain.openDrawer(GravityCompat.END)
        }
        binding.btnOgMainMenusEditBottomContainer.setOnClickListener {
            vm.deleteOgMainMenus(
                OptionGroupMainMenuMappersDeleteRequest(
                    adapter.items.filter { it.checked }.mapNotNull {
                        it.takeIf {
                            !it.mainCategoryCode.isNullOrBlank() &&
                            !it.middleCategoryCode.isNullOrBlank() &&
                            !it.subCategoryCode.isNullOrBlank() &&
                            !it.menuCode.isNullOrBlank()
                        }?.let {
                            OptionGroupMainMenuMappersDeleteRequest.OptionGroupMainMenuMapperDelete(
                                it.mainCategoryCode!!,
                                it.middleCategoryCode!!,
                                it.subCategoryCode!!,
                                it.menuCode!!,
                            )
                        }
                    }
                )
            ) {
                ogMainMenusVm.ogMainMenuMappers()
            }
        }

        binding.constraintOgMainMenusEditAllContainer.setOnClickListener {
            adapter.checkOrUncheckAll()
        }
    }

    private fun observeData() {
        vm.ogMainMenusEdit.observe(viewLifecycleOwner) {
            adapter.updateItems(it.ogMainMenuMappersEdit)
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

    override fun onDragStart(holder: OgMainMenusEditViewHolder) {
        itemTouchHelper.startDrag(holder)
    }

    override fun onDragOver() {
        Logger.i(adapter.items.toString())
        vm.changeMiddleCategoryPriorities(
            OptionGroupMainMenuMapperPrioritiesChangeRequest(
                adapter.items.mapIndexedNotNull { priority: Int, item ->
                    item.takeIf {
                        !it.mainCategoryCode.isNullOrBlank() &&
                                !it.middleCategoryCode.isNullOrBlank() &&
                                !it.subCategoryCode.isNullOrBlank() &&
                                !it.menuCode.isNullOrBlank()
                    }?.let {
                        OptionGroupMainMenuMapperPrioritiesChangeRequest.OptionGroupMainMenu(
                            it.mainCategoryCode!!,
                            it.middleCategoryCode!!,
                            it.subCategoryCode!!,
                            it.menuCode!!,
                            priority,
                        )
                    }
                }
            ),
        ) {
            ogMainMenusVm.ogMainMenuMappers()
        }
    }
}