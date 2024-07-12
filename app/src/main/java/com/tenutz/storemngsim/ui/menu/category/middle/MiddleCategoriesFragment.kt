package com.tenutz.storemngsim.ui.menu.category.middle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tenutz.storemngsim.databinding.FragmentMiddleCategoriesBinding
import com.tenutz.storemngsim.ui.menu.category.sub.args.SubCategoriesNavArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MiddleCategoriesFragment : Fragment() {

    private var _binding: FragmentMiddleCategoriesBinding? = null
    val binding: FragmentMiddleCategoriesBinding get() = _binding!!

    val args: MiddleCategoriesFragmentArgs by navArgs()

    private val vm: MiddleCategoriesViewModel by viewModels()

    private val adapter: MiddleCategoriesAdapter by lazy {
        MiddleCategoriesAdapter {
            it.categoryCode?.let { _ ->
                findNavController().navigate(
                    MiddleCategoriesFragmentDirections.actionMiddleCategoriesFragmentToSubCategoriesFragment(
                        SubCategoriesNavArgs(
                            it.storeCode,
                            args.mainCategory.categoryCode,
                            it.categoryCode,
                            it.categoryName,
                            it.use,
                            it.imageName,
                            it.imageUrl,
                            it.order,
                            it.createdAt,
                            it.lastModifiedAt,
                        )
                    )
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.middleCategories(args.mainCategory.categoryCode)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMiddleCategoriesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observeData()
    }

    private fun initViews() {
        binding.args = args.mainCategory
        binding.recyclerMiddleCategories.adapter = adapter
    }

    private fun observeData() {
        vm.middleCategories.observe(viewLifecycleOwner) {
            adapter.updateItems(it.middleCategories)
            binding.itemNotExists = adapter.itemCount == 0
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}