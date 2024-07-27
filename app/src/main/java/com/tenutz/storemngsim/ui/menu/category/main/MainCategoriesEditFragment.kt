package com.tenutz.storemngsim.ui.menu.category.main

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
import com.tenutz.storemngsim.data.datasource.api.dto.category.CategoriesDeleteRequest
import com.tenutz.storemngsim.data.datasource.api.dto.category.CategoryPrioritiesChangeRequest
import com.tenutz.storemngsim.databinding.FragmentMainCategoriesEditBinding
import com.tenutz.storemngsim.ui.base.BaseFragment
import com.tenutz.storemngsim.utils.ItemTouchHelperCallback
import com.tenutz.storemngsim.utils.OnDragListener
import com.tenutz.storemngsim.utils.ext.mainActivity
import com.tenutz.storemngsim.utils.ext.navigateToMainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainCategoriesEditFragment: BaseFragment(), OnDragListener<MainCategoriesEditViewHolder> {

    private var _binding: FragmentMainCategoriesEditBinding? = null
    val binding: FragmentMainCategoriesEditBinding get() = _binding!!

    val args: MainCategoriesEditFragmentArgs by navArgs()

    val vm: MainCategoriesEditViewModel by viewModels()

    val pVm: MainCategoriesViewModel by navGraphViewModels(R.id.navigation_main_category) {
        defaultViewModelProviderFactory
    }

    private val adapter: MainCategoriesEditAdapter by lazy {
        MainCategoriesEditAdapter(
            onCheckedChangeListener = {
                vm.updateCheckedItemCount()
            },
            onChangePrioritiesListener = {

            },
            onDragListener = this@MainCategoriesEditFragment,
        ).apply {
            setHasStableIds(true)
        }
    }

    private lateinit var itemTouchHelper: ItemTouchHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.setMainCategoriesEdit(args.mainCategories)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainCategoriesEditBinding.inflate(inflater, container, false)

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
        binding.recyclerMainCategoriesEdit.adapter = adapter
        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerMainCategoriesEdit)
    }

    private fun setOnClickListeners() {
        binding.imageMainCategoriesEditBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.imageMainCategoriesEditHome.setOnClickListener {
            mainActivity().navigateToMainFragment()
        }
        binding.imageMainCategoriesEditHamburger.setOnClickListener {
            mainActivity().binding.drawerMain.openDrawer(GravityCompat.END)
        }
        binding.btnMainCategoriesEditBottomContainer.setOnClickListener {
            vm.deleteMainCategories(
                CategoriesDeleteRequest(
                    adapter.items.filter { it.checked }.mapNotNull { it.categoryCode }
                )
            ) {
                pVm.mainCategories()
            }
        }

        binding.constraintMainCategoriesEditAllContainer.setOnClickListener {
            adapter.checkOrUncheckAll()
        }
    }

    private fun observeData() {
        vm.mainCategoriesEdit.observe(viewLifecycleOwner) {
            adapter.updateItems(it.mainCategoriesEdit)
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

    override fun onDragStart(holder: MainCategoriesEditViewHolder) {
        itemTouchHelper.startDrag(holder)
    }

    override fun onDragOver() {
        Logger.i(adapter.items.toString())
        vm.changeMainCategoryPriorities(
            CategoryPrioritiesChangeRequest(
                adapter.items.mapIndexedNotNull { priority: Int, item ->
                    item.categoryCode?.let {
                        CategoryPrioritiesChangeRequest.MainCategory(
                            item.categoryCode, priority
                        )
                    }
                }
            )
        ) {
            pVm.mainCategories()
        }
    }
}