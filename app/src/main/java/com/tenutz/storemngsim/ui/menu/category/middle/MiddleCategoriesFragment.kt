package com.tenutz.storemngsim.ui.menu.category.middle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.databinding.FragmentMiddleCategoriesBinding
import com.tenutz.storemngsim.ui.base.BaseFragment
import com.tenutz.storemngsim.ui.menu.category.main.MainCategoriesFragmentDirections
import com.tenutz.storemngsim.ui.menu.category.middle.args.MiddleCategoriesNavArgs
import com.tenutz.storemngsim.ui.menu.category.middle.bs.MiddleCategoriesBottomSheetDialog
import com.tenutz.storemngsim.ui.menu.category.sub.args.SubCategoriesNavArgs
import com.tenutz.storemngsim.utils.ext.mainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MiddleCategoriesFragment : BaseFragment() {

    private var _binding: FragmentMiddleCategoriesBinding? = null
    val binding: FragmentMiddleCategoriesBinding get() = _binding!!

    private val vm: MiddleCategoriesViewModel by navGraphViewModels(R.id.navigation_middle_category) {
        defaultViewModelProviderFactory
    }

    lateinit var args: MiddleCategoriesNavArgs

    private val adapter: MiddleCategoriesAdapter by lazy {
        MiddleCategoriesAdapter {
            MiddleCategoriesBottomSheetDialog(
                onClickListener = { id, _ ->
                    when (id) {
                        R.id.btn_bsmiddle_categories_sub -> {
                            it.categoryCode?.let { _ ->
                                MiddleCategoriesFragmentDirections.actionMiddleCategoriesFragmentToNavigationSubCategory().let { action ->
                                    findNavController().navigate(
                                        action.actionId,
                                        Bundle().apply {
                                            putParcelable(
                                                "middleCategory",
                                                SubCategoriesNavArgs(
                                                    it.storeCode,
                                                    args.categoryCode,
                                                    it.categoryCode,
                                                    it.categoryName,
                                                    it.use,
                                                    it.imageName,
                                                    it.imageUrl,
                                                    it.order,
                                                    it.tel,
                                                    it.address,
                                                    it.createdAt,
                                                    it.lastModifiedAt,
                                                )
                                            )
                                        }
                                    )
                                }
                            }
                        }
                        R.id.btn_bsmiddle_categories_details -> {
                            it.categoryCode?.let {
                                findNavController().navigate(
                                    MiddleCategoriesFragmentDirections.actionMiddleCategoriesFragmentToMiddleCategoryDetailsFragment(
                                        args.categoryCode,
                                        it,
                                    )
                                )
                            }
                        }
                    }
                }
            ).show(childFragmentManager, "mainCategoriesBottomSheetDialog")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        args = requireArguments().getParcelable("mainCategory")!!

        vm.middleCategories(args.categoryCode)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMiddleCategoriesBinding.inflate(inflater, container, false)

        binding.vm = vm
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observeData()
        setOnClickListeners()
    }

    private fun initViews() {
        binding.args = args
        binding.recyclerMiddleCategories.adapter = adapter
    }

    private fun observeData() {
        vm.middleCategories.observe(viewLifecycleOwner) {
            adapter.updateItems(it.middleCategories)
            binding.recyclerMiddleCategories.scrollToPosition(0)
        }
        vm.viewEvent.observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                when (it.first) {
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.imageMiddleCategoriesBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.imageMiddleCategoriesHome.setOnClickListener {
            findNavController().navigate(R.id.action_global_mainFragment)
        }
        binding.imageMiddleCategoriesHamburger.setOnClickListener {
            mainActivity().binding.drawerMain.openDrawer(GravityCompat.END)
        }
        binding.textMiddleCategoriesEdit.setOnClickListener {
            vm.middleCategories.value?.let {
                findNavController().navigate(
                    MiddleCategoriesFragmentDirections.actionMiddleCategoriesFragmentToMiddleCategoriesEditFragment(
                        args,
                        it,
                    )
                )
            }
        }
        binding.fabMiddleCategoriesAdd.setOnClickListener {
            findNavController().navigate(MiddleCategoriesFragmentDirections.actionMiddleCategoriesFragmentToMiddleCategoryAddFragment(args.categoryCode))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}