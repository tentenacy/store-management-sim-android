package com.tenutz.storemngsim.ui.menu.category.middle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.data.datasource.api.dto.category.MainCategoryUpdateRequest
import com.tenutz.storemngsim.data.datasource.api.dto.category.MiddleCategoryUpdateRequest
import com.tenutz.storemngsim.databinding.FragmentMainCategoriesBinding
import com.tenutz.storemngsim.databinding.FragmentMenuMngBinding
import com.tenutz.storemngsim.databinding.FragmentMiddleCategoriesBinding
import com.tenutz.storemngsim.databinding.FragmentMiddleCategoryDetailsBinding
import com.tenutz.storemngsim.ui.menu.category.main.MainCategoryDetailsFragmentArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MiddleCategoryDetailsFragment: Fragment() {

    private var _binding: FragmentMiddleCategoryDetailsBinding? = null
    val binding: FragmentMiddleCategoryDetailsBinding get() = _binding!!

    val args: MiddleCategoryDetailsFragmentArgs by navArgs()

    val vm: MiddleCategoryDetailsViewModel by viewModels()

    private val pVm: MiddleCategoriesViewModel by navGraphViewModels(R.id.navigation_middle_category) {
        defaultViewModelProviderFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.middleCategory(args.mainCategoryCode, args.middleCategoryCode)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMiddleCategoryDetailsBinding.inflate(inflater, container, false)

        binding.vm = vm
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.btnMiddleCategoryDetailsCancel.setOnClickListener {
            binding.vm = vm
            vm.switchToReadMode()
        }
        binding.btnMiddleCategoryDetailsSave.setOnClickListener {
            vm.updateMiddleCategory(
                args.mainCategoryCode,
                args.middleCategoryCode,
                MiddleCategoryUpdateRequest(
                    categoryName = binding.editMiddleCategoryDetailsName.text.toString(),
                    use = binding.radiogroupMiddleCategoryDetails.checkedRadioButtonId == R.id.radio_middle_category_details_use,
                    businessNumber = binding.editMiddleCategoryDetailsBizNo.text.toString(),
                    representativeName = binding.editMiddleCategoryDetailsOwnerName.text.toString(),
                    tel = binding.editMiddleCategoryDetailsPhone.text.toString(),
                    address = binding.editMiddleCategoryDetailsAddress.text.toString(),
                    tid = binding.editMiddleCategoryDetailsTid.text.toString(),
                )
            ) {
                pVm.middleCategories(args.mainCategoryCode)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}