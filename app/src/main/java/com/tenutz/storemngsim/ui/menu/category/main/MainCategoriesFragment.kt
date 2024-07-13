package com.tenutz.storemngsim.ui.menu.category.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.tenutz.storemngsim.NavigationMainCategoryDirections
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.storemngsim.data.datasource.sharedpref.Token
import com.tenutz.storemngsim.databinding.FragmentMainCategoriesBinding
import com.tenutz.storemngsim.ui.main.MainFragmentDirections
import com.tenutz.storemngsim.ui.menu.category.main.bs.MainCategoriesBottomSheetDialog
import com.tenutz.storemngsim.ui.menu.category.middle.MiddleCategoriesEditFragmentArgs
import com.tenutz.storemngsim.ui.menu.category.middle.args.MiddleCategoriesNavArgs
import com.tenutz.storemngsim.utils.ext.editTextObservable
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.addTo
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainCategoriesFragment : Fragment() {

    private val disposable = CompositeDisposable()

    private var _binding: FragmentMainCategoriesBinding? = null
    val binding: FragmentMainCategoriesBinding get() = _binding!!

    val vm: MainCategoriesViewModel by navGraphViewModels(R.id.navigation_main_category) {
        defaultViewModelProviderFactory
    }

    private val adapter: MainCategoriesAdapter by lazy {
        MainCategoriesAdapter {
            MainCategoriesBottomSheetDialog(
                onClickListener = { id, _ ->
                    when (id) {
                        R.id.btn_bsmain_categories_middle -> {
                            it.categoryCode?.let { _ ->
                                MainCategoriesFragmentDirections.actionMainCategoriesFragmentToNavigationMiddleCategory().let { action ->
                                    findNavController().navigate(
                                        action.actionId,
                                        Bundle().apply { putParcelable("mainCategory",
                                            MiddleCategoriesNavArgs(
                                                it.storeCode,
                                                it.categoryCode,
                                                it.categoryName,
                                                it.use,
                                                it.order,
                                                it.createdAt,
                                                it.lastModifiedAt,
                                            )
                                        ) }
                                    )
                                }
                            }
                        }
                        R.id.btn_bsmain_categories_details -> {
                            it.categoryCode?.let { findNavController().navigate(MainCategoriesFragmentDirections.actionMainCategoriesFragmentToMainCategoryDetailsFragment(it)) }
                        }
                    }

                },
            ).show(childFragmentManager, "mainCategoriesBottomSheetDialog")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.mainCategories()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainCategoriesBinding.inflate(inflater, container, false)
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
        binding.recyclerMainCategories.adapter = adapter
        editTextObservable(binding.editMainCategoriesSearch)
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                vm.mainCategories(it)
            }
            .addTo(disposable)
    }

    private fun observeData() {
        vm.mainCategories.observe(viewLifecycleOwner) {
            adapter.updateItems(it.mainCategories)
            binding.recyclerMainCategories.scrollToPosition(0)
        }
        vm.viewEvent.observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                when (it.first) {
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.textMainCategoriesEdit.setOnClickListener {
            vm.mainCategories.value?.let {
                findNavController().navigate(MainCategoriesFragmentDirections.actionMainCategoriesFragmentToMainCategoriesEditFragment(it))
            }
        }
        binding.fabMainCategoriesAdd.setOnClickListener {
            findNavController().navigate(MainCategoriesFragmentDirections.actionMainCategoriesFragmentToMainCategoryAddFragment())
        }
    }

    override fun onDestroyView() {
        disposable.dispose()
        super.onDestroyView()
        _binding = null
    }
}