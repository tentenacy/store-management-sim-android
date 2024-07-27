package com.tenutz.storemngsim.ui.menu.optionmenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.data.datasource.api.dto.option.OptionsResponse
import com.tenutz.storemngsim.databinding.FragmentOptionMenusBinding
import com.tenutz.storemngsim.ui.menu.optionmenu.base.NavOptionMenuFragment
import com.tenutz.storemngsim.ui.menu.optionmenu.bs.OptionMenusBottomSheetDialog
import com.tenutz.storemngsim.ui.menu.optionmenu.optiongroup.args.OmOptionGroupsNavArgs
import com.tenutz.storemngsim.utils.ext.editTextObservable
import com.tenutz.storemngsim.utils.ext.mainActivity
import com.tenutz.storemngsim.utils.ext.navigateToMainFragment
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class OptionMenusFragment: NavOptionMenuFragment() {

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

                                        (item as OptionsResponse.Option).let {
                                            findNavController().navigate(
                                                OptionMenusFragmentDirections.showOmOptionGroup(OmOptionGroupsNavArgs(
                                                    it.optionCode,
                                                    it.optionName,
                                                    it.imageUrl,
                                                    it.price,
                                                    it.use,
                                                ))
                                            )
                                        }
                                    }
                                    R.id.btn_bsoption_menus_details -> {
                                        (item as OptionsResponse.Option).let {
                                            findNavController().navigate(
                                                OptionMenusFragmentDirections.actionOptionMenusFragmentToOptionMenuDetailsFragment(it.optionCode)
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
            mainActivity().navigateToMainFragment()
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