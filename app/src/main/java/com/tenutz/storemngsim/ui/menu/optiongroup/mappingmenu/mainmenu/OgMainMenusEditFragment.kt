package com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.mainmenu

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
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.OptionGroupMainMenuMapperPrioritiesChangeRequest
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.OptionGroupMainMenuMappersDeleteRequest
import com.tenutz.storemngsim.databinding.*
import com.tenutz.storemngsim.ui.menu.category.main.MainCategoriesEditViewModel
import com.tenutz.storemngsim.ui.menu.category.middle.*
import com.tenutz.storemngsim.utils.ItemTouchHelperCallback
import com.tenutz.storemngsim.utils.OnDragListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OgMainMenusEditFragment: Fragment(), OnDragListener<OgMainMenusEditViewHolder> {

    private var _binding: FragmentOgMainMenusEditBinding? = null
    val binding: FragmentOgMainMenusEditBinding get() = _binding!!

    val args: OgMainMenusEditFragmentArgs by navArgs()

    val vm: OgMainMenusEditViewModel by viewModels()

    private val pVm: OgMainMenusViewModel by navGraphViewModels(R.id.navigation_og_mapping_menu) {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.setOgMainMenusEdit(args.ogMainMenuMappers)
    }

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
            findNavController().navigate(R.id.action_global_mainFragment)
        }
        binding.btnOgMainMenusEditBottomContainer.setOnClickListener {
            vm.deleteOgMainMenus(
                args.optionGroup.optionGroupCode,
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
                pVm.ogMainMenuMappers(args.optionGroup.optionGroupCode)
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
            args.optionGroup.optionGroupCode,
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
            pVm.ogMainMenuMappers(args.optionGroup.optionGroupCode)
        }
    }
}