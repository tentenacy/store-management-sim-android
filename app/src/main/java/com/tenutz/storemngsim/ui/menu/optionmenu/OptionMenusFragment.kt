package com.tenutz.storemngsim.ui.menu.optionmenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.data.datasource.api.dto.option.OptionsResponse
import com.tenutz.storemngsim.databinding.*
import com.tenutz.storemngsim.ui.base.BaseFragment
import com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.mainmenu.OgMainMenuAddViewModel
import com.tenutz.storemngsim.ui.menu.optionmenu.bs.OptionMenusBottomSheetDialog
import com.tenutz.storemngsim.ui.menu.optionmenu.optiongroup.args.OmOptionGroupsNavArgs
import com.tenutz.storemngsim.utils.ext.editTextObservable
import com.tenutz.storemngsim.utils.ext.mainActivity
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class OptionMenusFragment: BaseFragment() {

    private val disposable = CompositeDisposable()

    private var _binding: FragmentOptionMenusBinding? = null
    val binding: FragmentOptionMenusBinding get() = _binding!!

    val vm: OptionMenusViewModel by navGraphViewModels(R.id.navigation_option_menu) {
        defaultViewModelProviderFactory
    }

    private val adapter: OptionMenusAdapter by lazy {
        OptionMenusAdapter(
            onClickListener = { id, item ->
                when(id) {
                    R.id.constraint_ioption_menus_container -> {
                        OptionMenusBottomSheetDialog(
                            onClickListener = { id2, _ ->
                                when(id2) {
                                    R.id.btn_bsoption_menus_option_group -> {
                                        (item as OptionsResponse.Option).takeIf { !it.optionCode.isNullOrBlank() }?.let {
                                            OptionMenusFragmentDirections.actionOptionMenusFragmentToNavigationOmOptionGroup().let { action ->
                                                findNavController().navigate(
                                                    action.actionId,
                                                    Bundle().apply {
                                                        putParcelable(
                                                            "optionMenu",
                                                            OmOptionGroupsNavArgs(
                                                                it.storeCode,
                                                                it.optionCode!!,
                                                                it.optionName,
                                                                it.imageUrl,
                                                                it.outOfStock,
                                                                it.price,
                                                                it.use,
                                                            )
                                                        )
                                                    }
                                                )
                                            }
                                        }
                                    }
                                    R.id.btn_bsoption_menus_details -> {
                                        val optionMenu = item as OptionsResponse.Option
                                        optionMenu.optionCode?.let {
                                            findNavController().navigate(
                                                OptionMenusFragmentDirections.actionOptionMenusFragmentToOptionMenuDetailsFragment(it)
                                            )
                                        }
                                    }
                                }
                            }
                        ).show(childFragmentManager, "optionMenusBottomSheetDialog")
                    }
                }
            }
        ).apply {
            setHasStableIds(true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.optionMenus()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOptionMenusBinding.inflate(inflater, container, false)

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
        vm.optionMenus.observe(viewLifecycleOwner) {
            if(vm.hideRemoval.value == true) {
                adapter.updateItems(it.options.filter { it.use != null })
            } else {
                adapter.updateItems(it.options)
            }
            binding.recyclerOptionMenus.scrollToPosition(0)
        }
        vm.viewEvent.observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                when(it.first) {
                    OptionMenusViewModel.EVENT_HIDE_REMOVAL -> {
                        adapter.updateItems(vm.optionMenus.value?.options?.filter { it.use != null })
                    }
                    OptionMenusViewModel.EVENT_SHOW_REMOVAL -> {
                        adapter.updateItems(vm.optionMenus.value?.options)
                    }
                }
            }

        }
    }

    private fun initViews() {
        binding.recyclerOptionMenus.adapter = adapter
        editTextObservable(binding.editOptionMenusSearch)
            .debounce(500, TimeUnit.MILLISECONDS).skip(1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                vm.optionMenus(it)
            }
            .addTo(disposable)
    }

    private fun setOnClickListeners() {
        binding.imageOptionMenusBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.imageOptionMenusHome.setOnClickListener {
            findNavController().navigate(R.id.action_global_mainFragment)
        }
        binding.imageOptionMenusHamburger.setOnClickListener {
            mainActivity().binding.drawerMain.openDrawer(GravityCompat.END)
        }
        binding.textOptionMenusEdit.setOnClickListener {
            vm.optionMenus.value?.let {
                findNavController().navigate(OptionMenusFragmentDirections.actionOptionMenusFragmentToOptionMenusEditFragment(OptionsResponse(it.options.filter { it.use != null })))
            }
        }
        binding.fabOptionMenusAdd.setOnClickListener {
            findNavController().navigate(OptionMenusFragmentDirections.actionOptionMenusFragmentToOptionMenuAddFragment())
        }
        binding.textOptionMenusShowHideRemoval.setOnClickListener {
            if(vm.hideRemoval.value == true) {
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