package com.tenutz.storemngsim.ui.menu.category.sub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
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
import com.tenutz.storemngsim.data.datasource.api.dto.category.SubCategoriesResponse
import com.tenutz.storemngsim.databinding.*
import com.tenutz.storemngsim.ui.base.BaseFragment
import com.tenutz.storemngsim.ui.menu.category.main.MainCategoriesEditViewModel
import com.tenutz.storemngsim.ui.menu.category.middle.MiddleCategoriesEditViewHolder
import com.tenutz.storemngsim.ui.menu.category.sub.args.SubCategoriesEditArgs
import com.tenutz.storemngsim.utils.ItemTouchHelperCallback
import com.tenutz.storemngsim.utils.OnDragListener
import com.tenutz.storemngsim.utils.ext.mainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubCategoriesEditFragment: BaseFragment(), OnDragListener<SubCategoriesEditViewHolder.SubCategoryEditViewHolder> {

    private var _binding: FragmentSubCategoriesEditBinding? = null
    val binding: FragmentSubCategoriesEditBinding get() = _binding!!

    val args: SubCategoriesEditFragmentArgs by navArgs()

    val vm: SubCategoriesEditViewModel by viewModels()

    private val pVm: SubCategoriesViewModel by navGraphViewModels(R.id.navigation_sub_category) {
        defaultViewModelProviderFactory
    }

    private val adapter: SubCategoriesEditAdapter by lazy {
        SubCategoriesEditAdapter(
            args = args.middleCategory,
            vm = vm,
            onCheckedChangeListener = {
                vm.updateCheckedItemCount()
            },
            onClickListener = { id, item ->
                when (id) {
                    R.id.constraint_sub_categories_edit_top_all_container -> {
                        adapter.checkOrUncheckAll()
                    }
                }
            },

            onDragListener = this@SubCategoriesEditFragment,
        ).apply {
            setHasStableIds(true)
        }
    }

    private lateinit var itemTouchHelper: ItemTouchHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.setSubCategoriesEdit(args.subCategories)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSubCategoriesEditBinding.inflate(inflater, container, false)

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
        binding.recyclerSubCategoriesEdit.adapter = adapter
        adapter.updateItems(listOf(SubCategoriesEditItem.Header))
        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerSubCategoriesEdit)
    }

    private fun setOnClickListeners() {
        binding.imageSubCategoriesEditBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.imageSubCategoriesEditHome.setOnClickListener {
            findNavController().navigate(R.id.action_global_mainFragment)
        }
        binding.imageSubCategoriesEditHamburger.setOnClickListener {
            mainActivity().binding.drawerMain.openDrawer(GravityCompat.END)
        }
        binding.btnSubCategoriesEditBottomContainer.setOnClickListener {
            vm.deleteSubCategories(
                args.middleCategory.mainCategoryCode,
                args.middleCategory.categoryCode,
                CategoriesDeleteRequest(
                    adapter.items.filterIsInstance<SubCategoriesEditItem.Data>().filter { it.value.checked }.mapNotNull { it.value.categoryCode }
                )
            ) {
                pVm.subCategories(args.middleCategory.mainCategoryCode, args.middleCategory.categoryCode)
            }
        }
    }

    private fun observeData() {
        vm.subCategoriesEdit.observe(viewLifecycleOwner) {
            val arrayListOf = arrayListOf<SubCategoriesEditItem>()
            arrayListOf.add(SubCategoriesEditItem.Header)
            arrayListOf.addAll(it.subCategoriesEdit.map { SubCategoriesEditItem.Data(it) })
            adapter.updateItems(arrayListOf)
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

    override fun onDragStart(holder: SubCategoriesEditViewHolder.SubCategoryEditViewHolder) {
        itemTouchHelper.startDrag(holder)
    }

    override fun onDragOver() {
        Logger.i(adapter.items.toString())
        vm.changeSubCategoryPriorities(
            args.middleCategory.mainCategoryCode,
            args.middleCategory.categoryCode,
            CategoryPrioritiesChangeRequest(
                adapter.items.filterIsInstance<SubCategoriesEditItem.Data>().mapIndexedNotNull { priority: Int, item ->
                    item.value.categoryCode?.let {
                        CategoryPrioritiesChangeRequest.MainCategory(
                            it, priority
                        )
                    }
                }
            ),
        ) {
            pVm.subCategories(args.middleCategory.mainCategoryCode, args.middleCategory.categoryCode)
        }
    }
}