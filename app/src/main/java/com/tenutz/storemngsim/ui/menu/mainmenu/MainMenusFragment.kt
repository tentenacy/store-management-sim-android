 package com.tenutz.storemngsim.ui.menu.mainmenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.data.datasource.api.dto.menu.MainMenusResponse
import com.tenutz.storemngsim.databinding.*
import com.tenutz.storemngsim.ui.menu.mainmenu.MainMenusViewModel.Companion.EVENT_HIDE_REMOVAL
import com.tenutz.storemngsim.ui.menu.mainmenu.MainMenusViewModel.Companion.EVENT_SHOW_REMOVAL
import com.tenutz.storemngsim.ui.menu.mainmenu.args.MainMenusNavArgs
import com.tenutz.storemngsim.ui.menu.mainmenu.optiongroup.args.MmOptionGroupsNavArgs
import com.tenutz.storemngsim.ui.menu.mainmenu.bs.MainMenusBottomSheetDialog
import com.tenutz.storemngsim.utils.ext.editTextObservable
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainMenusFragment: Fragment() {

    private val disposable = CompositeDisposable()

    private var _binding: FragmentMainMenusBinding? = null
    val binding: FragmentMainMenusBinding get() = _binding!!

    lateinit var args: MainMenusNavArgs

    val vm: MainMenusViewModel by navGraphViewModels(R.id.navigation_main_menu) {
        defaultViewModelProviderFactory
    }

    private val adapter: MainMenusAdapter by lazy {
        MainMenusAdapter(
            onClickListener = { id, item ->
                when(id) {
                    R.id.constraint_imain_menus_container -> {
                        MainMenusBottomSheetDialog(
                            onClickListener = { id2, _ ->
                                when(id2) {
                                    R.id.btn_bsmain_menus_option_group -> {
                                        (item as MainMenusResponse.MainMenu).takeIf {
                                            !it.mainCategoryCode.isNullOrBlank() &&
                                            !it.middleCategoryCode.isNullOrBlank() &&
                                            !it.subCategoryCode.isNullOrBlank() &&
                                            !it.menuCode.isNullOrBlank()
                                        }?.let {
                                            MainMenusFragmentDirections.actionMainMenusFragmentToMmOptionGroupsFragment().let { action ->
                                                findNavController().navigate(
                                                    action.actionId,
                                                    Bundle().apply {
                                                        putParcelable(
                                                            "mainMenu",
                                                            MmOptionGroupsNavArgs(
                                                                it.storeCode,
                                                                it.mainCategoryCode!!,
                                                                it.middleCategoryCode!!,
                                                                it.subCategoryCode!!,
                                                                it.menuCode!!,
                                                                it.menuName,
                                                                it.imageUrl,
                                                                it.outOfStock,
                                                                it.price,
                                                                it.discountingPrice,
                                                                it.discountedPrice,
                                                                it.use,
                                                            )
                                                        )
                                                    }
                                                )
                                            }
                                        }
                                    }
                                    R.id.btn_bsmain_menus_details -> {
                                        val mainMenu = item as MainMenusResponse.MainMenu
                                        mainMenu.menuCode?.let {
                                            findNavController().navigate(
                                                MainMenusFragmentDirections.actionMainMenusFragmentToMainMenuDetailsFragment(
                                                    args.mainCategoryCode,
                                                    args.middleCategoryCode,
                                                    args.subCategoryCode,
                                                    mainMenu.menuCode,
                                                )
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireArguments().apply {
            args = MainMenusNavArgs(
                getString("mainCategoryCode")!!,
                getString("middleCategoryCode")!!,
                getString("subCategoryCode")!!,
            )
        }

        Logger.i(args.toString())

        vm.mainMenus(args.mainCategoryCode, args.middleCategoryCode, args.subCategoryCode)
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
            if(vm.hideRemoval.value == true) {
                adapter.updateItems(it.mainMenus.filter { it.use != null })
            } else {
                adapter.updateItems(it.mainMenus)
            }
            binding.recyclerMainMenus.scrollToPosition(0)
        }
        vm.viewEvent.observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                when(it.first) {
                    EVENT_HIDE_REMOVAL -> {
                        adapter.updateItems(vm.mainMenus.value?.mainMenus?.filter { it.use != null })
                    }
                    EVENT_SHOW_REMOVAL -> {
                        adapter.updateItems(vm.mainMenus.value?.mainMenus)
                    }
                }
            }

        }
    }

    private fun initViews() {
        binding.recyclerMainMenus.adapter = adapter
        editTextObservable(binding.editMainMenusSearch)
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                vm.mainMenus(args.mainCategoryCode, args.middleCategoryCode, args.subCategoryCode, it)
            }
            .addTo(disposable)
    }

    private fun setOnClickListeners() {
        binding.textMainMenusEdit.setOnClickListener {
            vm.mainMenus.value?.let {
                findNavController().navigate(MainMenusFragmentDirections.actionMainMenusFragmentToMainMenusEditFragment(args, MainMenusResponse(it.mainMenus.filter { it.use != null })))
            }
        }
        binding.fabMainMenusAdd.setOnClickListener {
            findNavController().navigate(MainMenusFragmentDirections.actionMainMenusFragmentToMainMenuAddFragment(args.mainCategoryCode, args.middleCategoryCode, args.subCategoryCode))
        }
        binding.textMainMenusShowHideRemoval.setOnClickListener {
            if(vm.hideRemoval.value == true) {
                vm.showRemoval()
            } else {
                vm.hideRemoval()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}