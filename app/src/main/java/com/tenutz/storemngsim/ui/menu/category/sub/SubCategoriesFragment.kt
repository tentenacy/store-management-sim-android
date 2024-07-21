package com.tenutz.storemngsim.ui.menu.category.sub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.data.datasource.api.dto.category.SubCategoriesResponse
import com.tenutz.storemngsim.databinding.FragmentSubCategoriesBinding
import com.tenutz.storemngsim.ui.menu.category.sub.args.SubCategoriesNavArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubCategoriesFragment : Fragment() {

    private var _binding: FragmentSubCategoriesBinding? = null
    val binding: FragmentSubCategoriesBinding get() = _binding!!

    lateinit var args: SubCategoriesNavArgs

    private val vm: SubCategoriesViewModel by navGraphViewModels(R.id.navigation_sub_category) {
        defaultViewModelProviderFactory
    }

    private val adapter: SubCategoriesAdapter by lazy {
        SubCategoriesAdapter(
            args,
            onClickListener = { id, item ->
                when (id) {
                    R.id.btn_sub_categories_top_details -> {

                    }
                    R.id.text_sub_categories_top_edit -> {
                        vm.subCategories.value?.let {
                            findNavController().navigate(
                                SubCategoriesFragmentDirections.actionSubCategoriesFragmentToSubCategoriesEditFragment(
                                    args,
                                    it
                                )
                            )
                        }
                    }
                    R.id.constraint_isub_categories_container -> {
                        (item as SubCategoriesResponse.SubCategory).categoryCode?.let {
                            findNavController().navigate(
                                SubCategoriesFragmentDirections.actionSubCategoriesFragmentToSubCategoryDetailsFragment(
                                    args.mainCategoryCode,
                                    args.categoryCode,
                                    it,
                                )
                            )
                        }
                    }
                }
            },
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        args = requireArguments().getParcelable("middleCategory")!!

        vm.subCategories(args.mainCategoryCode, args.categoryCode)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSubCategoriesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setOnClickListeners()
        observeData()
    }

    private fun setOnClickListeners() {
        binding.imageSubCategoriesBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.imageSubCategoriesHome.setOnClickListener {
            findNavController().navigate(R.id.action_global_mainFragment)
        }
        binding.fabSubCategoriesAdd.setOnClickListener {
            findNavController().navigate(
                SubCategoriesFragmentDirections.actionSubCategoriesFragmentToSubCategoryAddFragment(
                    args.mainCategoryCode,
                    args.categoryCode
                )
            )
        }
    }

    private fun initViews() {
        binding.recyclerSubCategories.adapter = adapter
        adapter.updateItems(listOf(SubCategoriesItem.Header))
    }

    private fun observeData() {
        vm.subCategories.observe(viewLifecycleOwner) {
            val arrayListOf = arrayListOf<SubCategoriesItem>()
            arrayListOf.add(SubCategoriesItem.Header)
            arrayListOf.addAll(it.subCategories.map { SubCategoriesItem.Data(it) })
            adapter.updateItems(arrayListOf)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}