package com.tenutz.storemngsim.ui.menu.category.middle

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
import com.tenutz.storemngsim.databinding.FragmentMiddleCategoriesEditBinding
import com.tenutz.storemngsim.ui.menu.category.main.MainCategoriesEditViewModel
import com.tenutz.storemngsim.utils.ItemTouchHelperCallback
import com.tenutz.storemngsim.utils.OnDragListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MiddleCategoriesEditFragment: Fragment(), OnDragListener<MiddleCategoriesEditViewHolder> {

    private var _binding: FragmentMiddleCategoriesEditBinding? = null
    val binding: FragmentMiddleCategoriesEditBinding get() = _binding!!

    val args: MiddleCategoriesEditFragmentArgs by navArgs()

    val vm: MiddleCategoriesEditViewModel by viewModels()

    private val pVm: MiddleCategoriesViewModel by navGraphViewModels(R.id.navigation_middle_category) {
        defaultViewModelProviderFactory
    }

    private val adapter: MiddleCategoriesEditAdapter by lazy {
        MiddleCategoriesEditAdapter(
            onCheckedChangeListener = {
                vm.updateCheckedItemCount()
            },
            onDragListener = this@MiddleCategoriesEditFragment,
        ).apply {
            setHasStableIds(true)
        }
    }

    private lateinit var itemTouchHelper: ItemTouchHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.setMiddleCategoriesEdit(args.middleCategories)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMiddleCategoriesEditBinding.inflate(inflater, container, false)

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
        binding.args = args.mainCategory
        binding.recyclerMiddleCategoriesEdit.adapter = adapter
        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerMiddleCategoriesEdit)
    }

    private fun setOnClickListeners() {
        binding.imageMiddleCategoriesEditBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.imageMiddleCategoriesEditHome.setOnClickListener {
            findNavController().navigate(R.id.action_global_mainFragment)
        }
        binding.btnMiddleCategoriesEditBottomContainer.setOnClickListener {
            vm.deleteMiddleCategories(
                args.mainCategory.categoryCode,
                CategoriesDeleteRequest(
                    adapter.items.filter { it.checked }.mapNotNull { it.categoryCode }
                )
            ) {
                pVm.middleCategories(args.mainCategory.categoryCode)
            }
        }

        binding.constraintMiddleCategoriesEditAllContainer.setOnClickListener {
            adapter.checkOrUncheckAll()
        }
    }

    private fun observeData() {
        vm.middleCategoriesEdit.observe(viewLifecycleOwner) {
            adapter.updateItems(it.middleCategoriesEdit)
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

    override fun onDragStart(holder: MiddleCategoriesEditViewHolder) {
        itemTouchHelper.startDrag(holder)
    }

    override fun onDragOver() {
        Logger.i(adapter.items.toString())
        vm.changeMiddleCategoryPriorities(
            args.mainCategory.categoryCode,
            CategoryPrioritiesChangeRequest(
                adapter.items.mapIndexedNotNull { priority: Int, item ->
                    item.categoryCode?.let {
                        CategoryPrioritiesChangeRequest.MainCategory(
                            item.categoryCode, priority
                        )
                    }
                }
            ),
        ) {
            pVm.middleCategories(args.mainCategory.categoryCode)
        }
    }
}