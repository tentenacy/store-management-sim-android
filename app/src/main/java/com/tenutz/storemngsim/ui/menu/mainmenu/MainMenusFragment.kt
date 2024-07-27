package com.tenutz.storemngsim.ui.menu.mainmenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.data.datasource.api.dto.menu.MainMenusResponse
import com.tenutz.storemngsim.databinding.FragmentMainMenusBinding
import com.tenutz.storemngsim.ui.menu.mainmenu.MainMenusViewModel.Companion.EVENT_HIDE_REMOVAL
import com.tenutz.storemngsim.ui.menu.mainmenu.MainMenusViewModel.Companion.EVENT_SHOW_REMOVAL
import com.tenutz.storemngsim.ui.menu.mainmenu.base.NavMainMenuFragment
import com.tenutz.storemngsim.ui.menu.mainmenu.bs.MainMenusBottomSheetDialog
import com.tenutz.storemngsim.ui.menu.mainmenu.optiongroup.args.MmOptionGroupsNavArgs
import com.tenutz.storemngsim.utils.ext.editTextObservable
import com.tenutz.storemngsim.utils.ext.mainActivity
import com.tenutz.storemngsim.utils.ext.navigateToMainFragment
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainMenusFragment : NavMainMenuFragment() {

    private val disposable = CompositeDisposable()

    private var _binding: FragmentMainMenusBinding? = null
    val binding: FragmentMainMenusBinding get() = _binding!!

    private val args: MainMenusFragmentArgs by navArgs()

    val vm: MainMenusViewModel by navGraphViewModels(R.id.navigation_main_menu) {
        defaultViewModelProviderFactory
    }

    private val adapter: MainMenusAdapter by lazy {
        MainMenusAdapter(
            onClickListener = { id, item ->
                when (id) {
                    R.id.constraint_imain_menus_container -> {
                        MainMenusBottomSheetDialog(
                            onClickListener = { id2, _ ->
                                when (id2) {
                                    R.id.btn_bsmain_menus_option_group -> {
                                        (item as MainMenusResponse.MainMenu).let {
                                            findNavController().navigate(
                                                MainMenusFragmentDirections.showMmOptionGroup(
                                                    args.subCategory,
                                                    MmOptionGroupsNavArgs(
                                                        it.menuCode,
                                                        it.menuName,
                                                        it.imageUrl,
                                                        it.outOfStock,
                                                        it.price,
                                                        it.discountingPrice,
                                                        it.discountedPrice,
                                                        it.use,
                                                    )
                                                )
                                            )
                                        }
                                    }

                                    R.id.btn_bsmain_menus_details -> {
                                        (item as MainMenusResponse.MainMenu).menuCode.let {
                                            findNavController().navigate(
                                                MainMenusFragmentDirections.actionMainMenusFragmentToMainMenuDetailsFragment(it, args.subCategory)
                                            )
                                        }
                                    }
                                }
                            }
                        ).show(childFragmentManager, "mainMenusBottomSheetDialog")
                    }
                }
            }
        ).apply {
            setHasStableIds(true)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainMenusBinding.inflate(inflater, container, false)

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

    private fun observeData() {
        vm.mainMenus.observe(viewLifecycleOwner) {

            if (vm.hideRemoval.value == true) {
                adapter.updateItems(it.mainMenus.filter { it.use != null }
                    .map { it.apply { subCategoryName = args.subCategory.subCategoryName } })
            } else {
                adapter.updateItems(it.mainMenus.map {
                    it.apply {
                        subCategoryName = args.subCategory.subCategoryName
                    }
                })
            }
            binding.recyclerMainMenus.scrollToPosition(0)
        }
        vm.viewEvent.observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                when (it.first) {
                    EVENT_HIDE_REMOVAL -> {
                        adapter.updateItems(vm.mainMenus.value?.mainMenus?.filter { it.use != null }
                            ?.map {
                                it.apply {
                                    subCategoryName = args.subCategory.subCategoryName
                                }
                            })
                    }

                    EVENT_SHOW_REMOVAL -> {
                        adapter.updateItems(vm.mainMenus.value?.mainMenus?.map {
                            it.apply {
                                subCategoryName = args.subCategory.subCategoryName
                            }
                        })
                    }
                }
            }
        }
    }

    private fun initViews() {
        binding.recyclerMainMenus.adapter = adapter
        editTextObservable(binding.editMainMenusSearch)
            .debounce(500, TimeUnit.MILLISECONDS).skip(1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                vm.mainMenus(searchText = it)
            }
            .addTo(disposable)
    }

    private fun setOnClickListeners() {
        binding.imageMainMenusBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.imageMainMenusHome.setOnClickListener {
            mainActivity().navigateToMainFragment()
        }
        binding.imageMainMenusHamburger.setOnClickListener {
            mainActivity().binding.drawerMain.openDrawer(GravityCompat.END)
        }
        binding.textMainMenusEdit.setOnClickListener {
            vm.mainMenus.value?.let {
                findNavController().navigate(
                    MainMenusFragmentDirections.actionMainMenusFragmentToMainMenusEditFragment(
                        MainMenusResponse(it.mainMenus.filter { it.use != null }), args.subCategory
                    )
                )
            }
        }
        binding.fabMainMenusAdd.setOnClickListener {
            findNavController().navigate(MainMenusFragmentDirections.actionMainMenusFragmentToMainMenuAddFragment(args.subCategory))
        }
        binding.textMainMenusShowHideRemoval.setOnClickListener {
            if (vm.hideRemoval.value == true) {
                vm.showRemoval()
            } else {
                vm.hideRemoval()
            }
        }
    }

    override fun onDestroyView() {
        disposable.clear()
        super.onDestroyView()
        _binding = null
    }
}