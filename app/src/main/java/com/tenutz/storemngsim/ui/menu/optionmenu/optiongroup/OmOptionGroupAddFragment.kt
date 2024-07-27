package com.tenutz.storemngsim.ui.menu.optionmenu.optiongroup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.data.datasource.api.dto.common.OptionGroupsMappedByRequest
import com.tenutz.storemngsim.databinding.FragmentOmOptionGroupAddBinding
import com.tenutz.storemngsim.ui.menu.optionmenu.optiongroup.base.NavOmOptionGroupFragment
import com.tenutz.storemngsim.utils.ext.editTextObservable
import com.tenutz.storemngsim.utils.ext.mainActivity
import com.tenutz.storemngsim.utils.ext.navigateToMainFragment
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class OmOptionGroupAddFragment: NavOmOptionGroupFragment() {

    private val disposable = CompositeDisposable()

    private var _binding: FragmentOmOptionGroupAddBinding? = null
    val binding: FragmentOmOptionGroupAddBinding get() = _binding!!

    val vm: OmOptionGroupAddViewModel by viewModels()

    private val pVm: OmOptionGroupsViewModel by navGraphViewModels(R.id.navigation_om_option_group) {
        defaultViewModelProviderFactory
    }

    private val adapter: OmOptionGroupAddAdapter by lazy {
        OmOptionGroupAddAdapter {
            vm.mapToOptionGroups(
                OptionGroupsMappedByRequest(listOf(it.optionGroupCode)),
            ) { pVm.omOptionGroups() }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOmOptionGroupAddBinding.inflate(inflater, container, false)

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

    private fun setOnClickListeners() {
        binding.imageOmOptionGroupAddBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.imageOmOptionGroupAddHome.setOnClickListener {
            mainActivity().navigateToMainFragment()
        }
        binding.imageOmOptionGroupAddHamburger.setOnClickListener {
            mainActivity().binding.drawerMain.openDrawer(GravityCompat.END)
        }
    }

    private fun observeData() {
        vm.omOptionGroups.observe(viewLifecycleOwner) {
            adapter.updateItems(it.optionOptionGroups)
        }
        vm.viewEvent.observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                when (it.first) {
                    OmOptionGroupAddViewModel.EVENT_NAVIGATE_UP -> {
                        findNavController().navigateUp()
                    }
                }
            }
        }
    }

    private fun initViews() {
        binding.recyclerOmOptionGroupAdd.adapter = adapter
        editTextObservable(binding.editOmOptionGroupAddSearch)
            .debounce(500, TimeUnit.MILLISECONDS).skip(1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                vm.omOptionGroups(it)
            }
            .addTo(disposable)
    }


    override fun onDestroyView() {
        disposable.clear()
        super.onDestroyView()
        _binding = null
    }
}