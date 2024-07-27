package com.tenutz.storemngsim.ui.menu.mainmenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.data.datasource.api.dto.menu.MenuPrioritiesChangeRequest
import com.tenutz.storemngsim.data.datasource.api.dto.menu.MenusDeleteRequest
import com.tenutz.storemngsim.databinding.FragmentMainMenusEditBinding
import com.tenutz.storemngsim.ui.menu.category.main.MainCategoriesEditViewModel
import com.tenutz.storemngsim.ui.menu.mainmenu.base.NavMainMenuFragment
import com.tenutz.storemngsim.utils.ItemTouchHelperCallback
import com.tenutz.storemngsim.utils.OnDragListener
import com.tenutz.storemngsim.utils.ext.mainActivity
import com.tenutz.storemngsim.utils.ext.navigateToMainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainMenusEditFragment: NavMainMenuFragment(), OnDragListener<MainMenusEditViewHolder> {

    private var _binding: FragmentMainMenusEditBinding? = null
    val binding: FragmentMainMenusEditBinding get() = _binding!!

    val vm: MainMenusEditViewModel by viewModels()

    private val pVm: MainMenusViewModel by navGraphViewModels(R.id.navigation_main_menu) {
        defaultViewModelProviderFactory
    }

    private val adapter: MainMenusEditAdapter by lazy {
        MainMenusEditAdapter(
            onCheckedChangeListener = {
                vm.updateCheckedItemCount()
            },
            onDragListener = this@MainMenusEditFragment,
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

        _binding = FragmentMainMenusEditBinding.inflate(inflater, container, false)

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
        binding.recyclerMainMenusEdit.adapter = adapter
        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerMainMenusEdit)
    }

    private fun setOnClickListeners() {
        binding.imageMainMenusEditBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.imageMainMenusEditHome.setOnClickListener {
            mainActivity().navigateToMainFragment()
        }
        binding.imageMainMenusEditHamburger.setOnClickListener {
            mainActivity().binding.drawerMain.openDrawer(GravityCompat.END)
        }
        binding.btnMainMenusEditBottomContainer.setOnClickListener {
            vm.deleteMainMenus(
                request = MenusDeleteRequest(
                    adapter.items.filter { it.checked }.mapNotNull { it.menuCode }
                )
            ) {
                pVm.mainMenus()
            }
        }

        binding.constraintMainMenusEditAllContainer.setOnClickListener {
            adapter.checkOrUncheckAll()
        }
    }

    private fun observeData() {
        vm.mainMenusEdit.observe(viewLifecycleOwner) {
            adapter.updateItems(it.mainMenusEdit)
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

    override fun onDragStart(holder: MainMenusEditViewHolder) {
        itemTouchHelper.startDrag(holder)
    }

    override fun onDragOver() {
        Logger.i(adapter.items.toString())
        vm.changeMainMenuPriorities(
            request = MenuPrioritiesChangeRequest(
                adapter.items.mapIndexedNotNull { priority: Int, item ->
                    item.menuCode?.let {
                        MenuPrioritiesChangeRequest.MainMenu(
                            item.menuCode, priority
                        )
                    }
                }
            ),
        ) {
            pVm.mainMenus()
        }
    }
}