package com.tenutz.storemngsim.ui.menu.category.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.data.datasource.api.dto.category.MainCategoryUpdateRequest
import com.tenutz.storemngsim.databinding.FragmentMainCategoryDetailsBinding
import com.tenutz.storemngsim.utils.ext.mainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainCategoryDetailsFragment : Fragment() {

    private var _binding: FragmentMainCategoryDetailsBinding? = null
    val binding: FragmentMainCategoryDetailsBinding get() = _binding!!

    val args: MainCategoryDetailsFragmentArgs by navArgs()

    val vm: MainCategoryDetailsViewModel by viewModels()
    private val pVm: MainCategoriesViewModel by navGraphViewModels(R.id.navigation_main_category) {
        defaultViewModelProviderFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.mainCategory(args.mainCategoryCode)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainCategoryDetailsBinding.inflate(inflater, container, false)

        binding.vm = vm
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.btnMainCategoryDetailsCancel.setOnClickListener {
            binding.vm = vm
            vm.switchToReadMode()
        }
        binding.btnMainCategoryDetailsSave.setOnClickListener {
            vm.updateMainCategory(
                args.mainCategoryCode,
                MainCategoryUpdateRequest(
                    binding.editMainCategoryDetailsCategoryName.text.toString(),
                    binding.radiogroupMainCategoryDetails.checkedRadioButtonId == R.id.radio_main_category_details_use
                )
            ) {
                pVm.mainCategories()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}