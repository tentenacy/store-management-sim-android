package com.tenutz.storemngsim.ui.menu.mainmenu.optiongroup

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
import com.tenutz.storemngsim.databinding.FragmentMmOptionGroupAddBinding
import com.tenutz.storemngsim.ui.menu.mainmenu.optiongroup.base.NavMmOptionGroupFragment
import com.tenutz.storemngsim.utils.ext.editTextObservable
import com.tenutz.storemngsim.utils.ext.mainActivity
import com.tenutz.storemngsim.utils.ext.navigateToMainFragment
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MmOptionGroupAddFragment: NavMmOptionGroupFragment() {

    private val disposable = CompositeDisposable()

    private var _binding: FragmentMmOptionGroupAddBinding? = null
    val binding: FragmentMmOptionGroupAddBinding get() = _binding!!

    val vm: MmOptionGroupAddViewModel by viewModels()

    private val pVm: MmOptionGroupsViewModel by navGraphViewModels(R.id.navigation_mm_option_group) {
        defaultViewModelProviderFactory
    }

    private val adapter: MmOptionGroupAddAdapter by lazy {
        MmOptionGroupAddAdapter {
            it.optionGroupCode?.let {
                vm.mapToOptionGroups(
                    request = OptionGroupsMappedByRequest(
                        listOf(it)
                    )
                ) { pVm.mmOptionGroups() }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMmOptionGroupAddBinding.inflate(inflater, container, false)

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
        binding.imageMmOptionGroupAddBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.imageMmOptionGroupAddHome.setOnClickListener {
            mainActivity().navigateToMainFragment()
        }
        binding.imageMmOptionGroupAddHamburger.setOnClickListener {
            mainActivity().binding.drawerMain.openDrawer(GravityCompat.END)
        }
    }

    private fun observeData() {
        vm.mmOptionGroups.observe(viewLifecycleOwner) {
            adapter.updateItems(it.mainMenuOptionGroups)
        }
        vm.viewEvent.observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                when (it.first) {
                    MmOptionGroupAddViewModel.EVENT_NAVIGATE_UP -> {
                        findNavController().navigateUp()
                    }
                }
            }
        }
    }

    private fun initViews() {
        binding.recyclerMmOptionGroupAdd.adapter = adapter
        editTextObservable(binding.editMmOptionGroupAddSearch)
            .debounce(500, TimeUnit.MILLISECONDS).skip(1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                vm.mmOptionGroups()
            }
            .addTo(disposable)
    }


    override fun onDestroyView() {
        disposable.clear()
        super.onDestroyView()
        _binding = null
    }
}