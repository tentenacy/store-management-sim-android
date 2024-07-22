package com.tenutz.storemngsim.ui.menu.category.sub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.data.datasource.api.dto.category.MainCategoryUpdateRequest
import com.tenutz.storemngsim.data.datasource.api.dto.category.MiddleCategoryCreateRequest
import com.tenutz.storemngsim.data.datasource.api.dto.category.SubCategoryCreateRequest
import com.tenutz.storemngsim.databinding.*
import com.tenutz.storemngsim.ui.base.BaseFragment
import com.tenutz.storemngsim.ui.menu.category.main.MainCategoryAddViewModel
import com.tenutz.storemngsim.utils.MyToast
import com.tenutz.storemngsim.utils.ext.mainActivity
import com.tenutz.storemngsim.utils.validation.Validator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubCategoryAddFragment: BaseFragment() {

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
                    SubCategoryAddViewModel.EVENT_NAVIGATE_UP -> {
                        findNavController().navigateUp()
                    }
                    SubCategoryAddViewModel.EVENT_TOAST -> {
                        MyToast.create(mainActivity(), it.second as String, 80)?.show()
                    }
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.imageSubCategoryAddBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.imageSubCategoryAddHome.setOnClickListener {
            findNavController().navigate(R.id.action_global_mainFragment)
        }
        binding.imageSubCategoryAddHamburger.setOnClickListener {
            mainActivity().binding.drawerMain.openDrawer(GravityCompat.END)
        }
        binding.btnSubCategoryAddSave.setOnClickListener {
            Validator.validate(
                onValidation = {
                    Validator.validateCategoryName(binding.editSubCategoryAddCategoryName.text.toString(), true)
                    Validator.validateCategoryCode(binding.editSubCategoryAddCategoryCode.text.toString(), true)
                },
                onSuccess = {
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
                },
                onFailure = { e ->
                    MyToast.create(mainActivity(), e.errorCode.message, 80)?.show()
                },
            )
        }
    }
}