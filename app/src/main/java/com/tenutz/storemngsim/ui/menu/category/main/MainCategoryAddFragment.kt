package com.tenutz.storemngsim.ui.menu.category.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.data.datasource.api.dto.category.MainCategoryCreateRequest
import com.tenutz.storemngsim.databinding.FragmentMainCategoryAddBinding
import com.tenutz.storemngsim.ui.base.BaseFragment
import com.tenutz.storemngsim.ui.menu.category.main.MainCategoryAddViewModel.Companion.EVENT_NAVIGATE_UP
import com.tenutz.storemngsim.ui.menu.category.main.MainCategoryAddViewModel.Companion.EVENT_TOAST
import com.tenutz.storemngsim.utils.MyToast
import com.tenutz.storemngsim.utils.validation.Validator
import com.tenutz.storemngsim.utils.validation.err.base.ValidationException
import com.tenutz.storemngsim.utils.ext.mainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainCategoryAddFragment: BaseFragment() {

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

        setOnClickListeners()
        observeData()
    }

    private fun observeData() {
        vm.viewEvent.observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                when (it.first) {
                    EVENT_NAVIGATE_UP -> {
                        findNavController().navigateUp()
                    }
                    EVENT_TOAST -> {
                        MyToast.create(mainActivity(), it.second as String, 80)?.show()
                    }
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.imageMainCategoryAddBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.imageMainCategoryAddHome.setOnClickListener {
            findNavController().navigate(R.id.action_global_mainFragment)
        }
        binding.imageMainCategoryAddHamburger.setOnClickListener {
            mainActivity().binding.drawerMain.openDrawer(GravityCompat.END)
        }
        binding.btnMainCategoryAddSave.setOnClickListener {

            Validator.validate(
                onValidation = {
                    Validator.validateCategoryName(binding.editMainCategoryAddCategoryName.text.toString(), true)
                    Validator.validateCategoryCode(binding.editMainCategoryAddCategoryCode.text.toString(), true)
                },
                onSuccess = {
                    vm.createMainCategory(
                        MainCategoryCreateRequest(
                            binding.editMainCategoryAddCategoryCode.text.toString(),
                            binding.editMainCategoryAddCategoryName.text.toString(),
                            binding.radiogroupMainCategoryAdd.checkedRadioButtonId == R.id.radio_main_category_add_use
                        )
                    ) {
                        pVm.mainCategories()
                    }
                },
                onFailure = { e ->
                    MyToast.create(mainActivity(), e.errorCode.message, 80)?.show()
                },
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}