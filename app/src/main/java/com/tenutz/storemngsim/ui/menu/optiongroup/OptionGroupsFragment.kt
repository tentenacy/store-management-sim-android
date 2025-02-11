package com.tenutz.storemngsim.ui.menu.optiongroup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.databinding.FragmentOptionGroupsBinding
import com.tenutz.storemngsim.ui.menu.optiongroup.base.NavOptionGroupFragment
import com.tenutz.storemngsim.ui.menu.optiongroup.bs.OptionGroupsBottomSheetDialog
import com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.args.MappingMenusNavArgs
import com.tenutz.storemngsim.utils.ext.editTextObservable
import com.tenutz.storemngsim.utils.ext.mainActivity
import com.tenutz.storemngsim.utils.ext.navigateToMainFragment
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class OptionGroupsFragment : NavOptionGroupFragment() {

    private val disposable = CompositeDisposable()

    private var _binding: FragmentOptionGroupsBinding? = null
    val binding: FragmentOptionGroupsBinding get() = _binding!!

    val vm: OptionGroupsViewModel by navGraphViewModels(R.id.navigation_option_group) {
        defaultViewModelProviderFactory
    }

    private val adapter: OptionGroupsAdapter by lazy {
        OptionGroupsAdapter {
            OptionGroupsBottomSheetDialog(
                onClickListener = { id, _ ->
                    when (id) {
                        R.id.btn_bsoption_groups_mapping_menus -> {
                            findNavController().navigate(
                                OptionGroupsFragmentDirections.showOgMappingMenu(MappingMenusNavArgs(
                                    it.optionGroupCode,
                                    it.optionGroupName,
                                    it.toggleSelect,
                                    it.required,
                                ))
                            )
                        }

                        R.id.btn_bsoption_groups_details -> {
                            findNavController().navigate(
                                OptionGroupsFragmentDirections.actionOptionGroupsFragmentToOptionGroupDetailsFragment(
                                    it.optionGroupCode
                                )
                            )
                        }
                    }
                },
            ).show(childFragmentManager, "optionGroupsBottomSheetDialog")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOptionGroupsBinding.inflate(inflater, container, false)
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
        binding.recyclerOptionGroups.adapter = adapter
        editTextObservable(binding.editOptionGroupsSearch)
            .debounce(500, TimeUnit.MILLISECONDS).skip(1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                vm.optionGroups(it)
            }
            .addTo(disposable)
    }

    private fun observeData() {
        vm.optionGroups.observe(viewLifecycleOwner) {
            adapter.updateItems(it.optionGroups)
            binding.recyclerOptionGroups.scrollToPosition(0)
        }
        vm.viewEvent.observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                when (it.first) {
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.imageOptionGroupsBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.imageOptionGroupsHome.setOnClickListener {
            mainActivity().navigateToMainFragment()
        }
        binding.imageOptionGroupsHamburger.setOnClickListener {
            mainActivity().binding.drawerMain.openDrawer(GravityCompat.END)
        }
        binding.textOptionGroupsEdit.setOnClickListener {
            vm.optionGroups.value?.let {
                findNavController().navigate(
                    OptionGroupsFragmentDirections.actionOptionGroupsFragmentToOptionGroupsEditFragment(it)
                )
            }
        }
        binding.fabOptionGroupsAdd.setOnClickListener {
            findNavController().navigate(OptionGroupsFragmentDirections.actionOptionGroupsFragmentToOptionGroupAddFragment())
        }
    }

    override fun onDestroyView() {
        disposable.clear()
        super.onDestroyView()
        _binding = null
    }
}