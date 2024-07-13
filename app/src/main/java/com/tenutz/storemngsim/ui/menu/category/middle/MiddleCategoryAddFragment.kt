package com.tenutz.storemngsim.ui.menu.category.middle

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
import com.tenutz.storemngsim.data.datasource.api.dto.category.MainCategoryCreateRequest
import com.tenutz.storemngsim.data.datasource.api.dto.category.MiddleCategoryCreateRequest
import com.tenutz.storemngsim.databinding.FragmentMainCategoriesBinding
import com.tenutz.storemngsim.databinding.FragmentMenuMngBinding
import com.tenutz.storemngsim.databinding.FragmentMiddleCategoriesBinding
import com.tenutz.storemngsim.databinding.FragmentMiddleCategoryAddBinding
import com.tenutz.storemngsim.ui.menu.category.main.MainCategoryAddViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MiddleCategoryAddFragment: Fragment() {

    private var _binding: FragmentMiddleCategoryAddBinding? = null
    val binding: FragmentMiddleCategoryAddBinding get() = _binding!!

    val args: MiddleCategoryAddFragmentArgs by navArgs()

    val vm: MiddleCategoryAddViewModel by viewModels()

    private val pVm: MiddleCategoriesViewModel by navGraphViewModels(R.id.navigation_middle_category) {
        defaultViewModelProviderFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMiddleCategoryAddBinding.inflate(inflater, container, false)

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
        binding.btnMiddleCategoryAddSave.setOnClickListener {
            vm.createMiddleCategory(
                args.mainCategoryCode,
                MiddleCategoryCreateRequest(
                    categoryCode = binding.editMiddleCategoryAddCode.text.toString(),
                    categoryName = binding.editMiddleCategoryAddName.text.toString(),
                    use = binding.radiogroupMiddleCategoryAdd.checkedRadioButtonId == R.id.radio_middle_category_add_use,
                    businessNumber = binding.editMiddleCategoryAddBizNo.text.toString(),
                    representativeName = binding.editMiddleCategoryAddOwnerName.text.toString(),
                    tel = binding.editMiddleCategoryAddPhone.text.toString(),
                    address = binding.editMiddleCategoryAddAddress.text.toString(),
                    tid = binding.editMiddleCategoryAddTid.text.toString(),
                )
            ) {
                pVm.middleCategories(args.mainCategoryCode)
            }
        }
    }
}