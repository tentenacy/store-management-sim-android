package com.tenutz.storemngsim.ui.menu.category.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.data.datasource.api.dto.category.MainCategoryCreateRequest
import com.tenutz.storemngsim.databinding.FragmentMainCategoriesBinding
import com.tenutz.storemngsim.databinding.FragmentMainCategoryAddBinding
import com.tenutz.storemngsim.databinding.FragmentMenuMngBinding
import com.tenutz.storemngsim.ui.menu.category.main.MainCategoryAddViewModel.Companion.EVENT_NAVIGATE_UP
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainCategoryAddFragment: Fragment() {

    private var _binding: FragmentMainCategoryAddBinding? = null
    val binding: FragmentMainCategoryAddBinding get() = _binding!!

    val vm: MainCategoryAddViewModel by viewModels()

    val pVm: MainCategoriesViewModel by navGraphViewModels(R.id.navigation_main_category) {
        defaultViewModelProviderFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainCategoryAddBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnMainCategoryAddSave.setOnClickListener {
            vm.createMainCategory(
                MainCategoryCreateRequest(
                    binding.editMainCategoryAddCategoryCode.text.toString(),
                    binding.editMainCategoryAddCategoryName.text.toString(),
                    binding.radiogroupMainCategoryAdd.checkedRadioButtonId == R.id.radio_main_category_add_use
                )
            ) {
                pVm.mainCategories()
            }
        }
        vm.viewEvent.observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                when(it.first) {
                    EVENT_NAVIGATE_UP -> {
                        findNavController().navigateUp()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}