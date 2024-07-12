package com.tenutz.storemngsim.ui.menu.category.sub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewbinding.ViewBindings
import com.tenutz.storemngsim.databinding.FragmentMainCategoriesBinding
import com.tenutz.storemngsim.databinding.FragmentMenuMngBinding
import com.tenutz.storemngsim.databinding.FragmentMiddleCategoriesBinding
import com.tenutz.storemngsim.databinding.FragmentSubCategoriesBinding
import com.tenutz.storemngsim.ui.menu.category.middle.MiddleCategoriesAdapter
import com.tenutz.storemngsim.ui.menu.category.middle.MiddleCategoriesFragmentArgs
import com.tenutz.storemngsim.ui.menu.category.middle.MiddleCategoriesFragmentDirections
import com.tenutz.storemngsim.ui.menu.category.middle.MiddleCategoriesViewModel
import com.tenutz.storemngsim.ui.menu.category.sub.args.SubCategoriesNavArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubCategoriesFragment: Fragment() {

    private var _binding: FragmentSubCategoriesBinding? = null
    val binding: FragmentSubCategoriesBinding get() = _binding!!

    val args: SubCategoriesFragmentArgs by navArgs()

    private val vm: SubCategoriesViewModel by viewModels()

    private val adapter: SubCategoriesAdapter by lazy {
        SubCategoriesAdapter(
            args.middleCategory,
            listener = {

            },
            detailsOnClickListener = {

            },
            editOnClickListener = {

            },
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.subCategories(args.middleCategory.mainCategoryCode, args.middleCategory.categoryCode)
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
        observeData()
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