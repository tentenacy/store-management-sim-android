package com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.optionmenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.OptionsMappedByRequest
import com.tenutz.storemngsim.databinding.FragmentOgOptionMenuAddBinding
import com.tenutz.storemngsim.ui.base.BaseFragment
import com.tenutz.storemngsim.ui.menu.mainmenu.optiongroup.MmOptionGroupAddViewModel
import com.tenutz.storemngsim.utils.ext.editTextObservable
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class OgOptionMenuAddFragment: BaseFragment() {

    private val disposable = CompositeDisposable()

    private var _binding: FragmentOgOptionMenuAddBinding? = null
    val binding: FragmentOgOptionMenuAddBinding get() = _binding!!

    val args: OgOptionMenuAddFragmentArgs by navArgs()

    val vm: OgOptionMenuAddViewModel by viewModels()

    private val pVm: OgOptionMenusViewModel by navGraphViewModels(R.id.navigation_og_mapping_menu) {
        defaultViewModelProviderFactory
    }

    private val adapter: OgOptionMenuAddAdapter by lazy {
        OgOptionMenuAddAdapter(
        ) {
            it.optionCode?.let {
                vm.mapToOptionMenus(
                    args.optionGroupCode,
                    OptionsMappedByRequest(
                        listOf(it)
                    )
                ) {
                    pVm.ogOptionMenuMappers(
                        args.optionGroupCode,
                    )
                }
            }
        }.apply {
            setHasStableIds(true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.ogOptionMenusAdd(args.optionGroupCode)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOgOptionMenuAddBinding.inflate(inflater, container, false)

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
        binding.imageOgOptionMenuAddBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observeData() {
        vm.ogOptionMenusAdd.observe(viewLifecycleOwner) {
            adapter.updateItems(it.ogOptionMenus)
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
        binding.recyclerOgOptionMenuAdd.adapter = adapter
        editTextObservable(binding.editOgOptionMenuAddSearch)
            .debounce(500, TimeUnit.MILLISECONDS).skip(1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                vm.ogOptionMenusAdd(args.optionGroupCode, it)
            }
            .addTo(disposable)
    }

    override fun onDestroyView() {
        disposable.clear()
        super.onDestroyView()
        _binding = null
    }
}