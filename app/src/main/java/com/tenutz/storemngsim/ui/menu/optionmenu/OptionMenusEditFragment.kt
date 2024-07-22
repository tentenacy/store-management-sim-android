package com.tenutz.storemngsim.ui.menu.optionmenu

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
import com.tenutz.storemngsim.data.datasource.api.dto.menu.MenuPrioritiesChangeRequest
import com.tenutz.storemngsim.data.datasource.api.dto.menu.MenusDeleteRequest
import com.tenutz.storemngsim.data.datasource.api.dto.option.OptionsDeleteRequest
import com.tenutz.storemngsim.databinding.*
import com.tenutz.storemngsim.ui.base.BaseFragment
import com.tenutz.storemngsim.ui.menu.category.main.MainCategoriesEditViewModel
import com.tenutz.storemngsim.ui.menu.mainmenu.*
import com.tenutz.storemngsim.utils.ItemTouchHelperCallback
import com.tenutz.storemngsim.utils.OnDragListener
import com.tenutz.storemngsim.utils.ext.mainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OptionMenusEditFragment: BaseFragment() {

    private var _binding: FragmentOptionMenusEditBinding? = null
    val binding: FragmentOptionMenusEditBinding get() = _binding!!

    val args: OptionMenusEditFragmentArgs by navArgs()

    val vm: OptionMenusEditViewModel by viewModels()

    private val pVm: OptionMenusViewModel by navGraphViewModels(R.id.navigation_option_menu) {
        defaultViewModelProviderFactory
    }

    private val adapter: OptionMenusEditAdapter by lazy {
        OptionMenusEditAdapter(
            onCheckedChangeListener = {
                vm.updateCheckedItemCount()
            },
        ).apply {
            setHasStableIds(true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.setOptionMenusEdit(args.optionMenus)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOptionMenusEditBinding.inflate(inflater, container, false)

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
        binding.recyclerOptionMenusEdit.adapter = adapter
    }

    private fun setOnClickListeners() {
        binding.imageOptionMenusEditBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.imageOptionMenusEditHome.setOnClickListener {
            findNavController().navigate(R.id.action_global_mainFragment)
        }
        binding.imageOptionMenusEditHamburger.setOnClickListener {
            mainActivity().binding.drawerMain.openDrawer(GravityCompat.END)
        }
        binding.btnOptionMenusEditBottomContainer.setOnClickListener {
            vm.deleteOptionMenus(
                OptionsDeleteRequest(
                    adapter.items.filter { it.checked }.mapNotNull { it.optionCode }
                )
            ) {
                pVm.optionMenus()
            }
        }

        binding.constraintOptionMenusEditAllContainer.setOnClickListener {
            adapter.checkOrUncheckAll()
        }
    }

    private fun observeData() {
        vm.optionMenusEdit.observe(viewLifecycleOwner) {
            adapter.updateItems(it.optionMenusEdit)
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
}