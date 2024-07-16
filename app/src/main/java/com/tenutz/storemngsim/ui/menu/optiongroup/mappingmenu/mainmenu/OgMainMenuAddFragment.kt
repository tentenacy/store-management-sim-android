package com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.mainmenu

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
import com.tenutz.storemngsim.data.datasource.api.dto.common.MainMenuSearchRequest
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.MainMenusMappedByRequest
import com.tenutz.storemngsim.databinding.FragmentOgMainMenuAddBinding
import com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.mainmenu.OgMainMenuAddViewModel.Companion.EVENT_NAVIGATE_UP
import com.tenutz.storemngsim.utils.ext.editTextObservable
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class OgMainMenuAddFragment: Fragment() {

    private val disposable = CompositeDisposable()

    private var _binding: FragmentOgMainMenuAddBinding? = null
    val binding: FragmentOgMainMenuAddBinding get() = _binding!!

    val args: OgMainMenuAddFragmentArgs by navArgs()

    val vm: OgMainMenuAddViewModel by viewModels()

    private val pVm: OgMainMenusViewModel by navGraphViewModels(R.id.navigation_og_mapping_menu) {
        defaultViewModelProviderFactory
    }

    private val adapter: OgMainMenuAddAdapter by lazy {
        OgMainMenuAddAdapter(
            onExpandedChangeListener = {
                vm.updateExpandedItemCount(it)
            },
        ) {
            it.takeIf {
                !it.mainCategoryCode.isNullOrBlank() &&
                !it.middleCategoryCode.isNullOrBlank() &&
                !it.subCategoryCode.isNullOrBlank() &&
                !it.menuCode.isNullOrBlank()
            }?.let {
                vm.mapToMainMenus(
                    args.optionGroupCode,
                    MainMenusMappedByRequest(
                        listOf(MainMenusMappedByRequest.MainMenuMappedBy(
                            it.mainCategoryCode!!,
                            it.middleCategoryCode!!,
                            it.subCategoryCode!!,
                            it.menuCode!!,
                        ))
                    )
                ) {
                    pVm.ogMainMenuMappers(
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

        vm.ogMainMenusAdd(
            args.optionGroupCode,
            MainMenuSearchRequest(
                args.mainCategoryCode,
                args.middleCategoryCode,
                args.subCategoryCode,
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOgMainMenuAddBinding.inflate(inflater, container, false)

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
        binding.textOgMappingMenusEditExpandContractAll.setOnClickListener {
            adapter.expandOrContractAll()
        }
    }

    private fun observeData() {
        vm.ogMainMenusAdd.observe(viewLifecycleOwner) {
            adapter.updateItems(it.ogMainMenus)
        }
        vm.viewEvent.observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                when (it.first) {
                    EVENT_NAVIGATE_UP -> {
                        findNavController().navigateUp()
                    }
                }
            }
        }
    }

    private fun initViews() {
        binding.recyclerOgMainMenuAdd.adapter = adapter
        editTextObservable(binding.editOgMainMenuAddSearch)
            .debounce(500, TimeUnit.MILLISECONDS).skip(1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                vm.ogMainMenusAdd(
                    args.optionGroupCode,
                    MainMenuSearchRequest(
                        args.mainCategoryCode,
                        args.middleCategoryCode,
                        args.subCategoryCode,
                    )
                )
            }
            .addTo(disposable)
    }

    override fun onDestroyView() {
        disposable.clear()
        super.onDestroyView()
        _binding = null
    }
}