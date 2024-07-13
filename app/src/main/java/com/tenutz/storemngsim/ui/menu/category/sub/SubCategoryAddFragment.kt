package com.tenutz.storemngsim.ui.menu.category.sub

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
import com.tenutz.storemngsim.data.datasource.api.dto.category.MiddleCategoryCreateRequest
import com.tenutz.storemngsim.data.datasource.api.dto.category.SubCategoryCreateRequest
import com.tenutz.storemngsim.databinding.*
import com.tenutz.storemngsim.ui.menu.category.main.MainCategoryAddViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubCategoryAddFragment: Fragment() {

    private var _binding: FragmentSubCategoryAddBinding? = null
    val binding: FragmentSubCategoryAddBinding get() = _binding!!

    val args: SubCategoryAddFragmentArgs by navArgs()

    val vm: SubCategoryAddViewModel by viewModels()

    private val pVm: SubCategoriesViewModel by navGraphViewModels(R.id.navigation_sub_category) {
        defaultViewModelProviderFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSubCategoryAddBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
        observeData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeData() {
        vm.viewEvent.observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                when (it.first) {
                    MainCategoryAddViewModel.EVENT_NAVIGATE_UP -> {
                        findNavController().navigateUp()
                    }
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.btnSubCategoryAddSave.setOnClickListener {
            vm.createSubCategory(
                args.mainCategoryCode,
                args.middleCategoryCode,
                SubCategoryCreateRequest(
                    categoryCode = binding.editSubCategoryAddCategoryCode.text.toString(),
                    categoryName = binding.editSubCategoryAddCategoryName.text.toString(),
                    use = binding.radiogroupSubCategoryAdd.checkedRadioButtonId == R.id.radio_sub_category_add_use,
                )
            ) {
                pVm.subCategories(args.mainCategoryCode, args.middleCategoryCode)
            }
        }
    }
}