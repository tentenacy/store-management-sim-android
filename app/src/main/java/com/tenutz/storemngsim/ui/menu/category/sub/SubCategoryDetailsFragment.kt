package com.tenutz.storemngsim.ui.menu.category.sub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.data.datasource.api.dto.category.MiddleCategoryUpdateRequest
import com.tenutz.storemngsim.data.datasource.api.dto.category.SubCategoryUpdateRequest
import com.tenutz.storemngsim.databinding.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubCategoryDetailsFragment: Fragment() {

    private var _binding: FragmentSubCategoryDetailsBinding? = null
    val binding: FragmentSubCategoryDetailsBinding get() = _binding!!

    val args: SubCategoryDetailsFragmentArgs by navArgs()

    val vm: SubCategoryDetailsViewModel by viewModels()

    private val pVm: SubCategoriesViewModel by navGraphViewModels(R.id.navigation_sub_category) {
        defaultViewModelProviderFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.subCategory(args.mainCategoryCode, args.middleCategoryCode, args.subCategoryCode)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSubCategoryDetailsBinding.inflate(inflater, container, false)

        binding.vm = vm
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.btnSubCategoryDetailsCancel.setOnClickListener {
            binding.vm = vm
            vm.switchToReadMode()
        }
        binding.btnSubCategoryDetailsSave.setOnClickListener {
            vm.updateSubCategory(
                args.mainCategoryCode,
                args.middleCategoryCode,
                args.subCategoryCode,
                SubCategoryUpdateRequest(
                    categoryName = binding.editSubCategoryDetailsCategoryName.text.toString(),
                    use = binding.radiogroupSubCategoryDetails.checkedRadioButtonId == R.id.radio_sub_category_details_use,
                )
            ) {
                pVm.subCategories(args.mainCategoryCode, args.middleCategoryCode)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}