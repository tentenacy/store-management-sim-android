package com.tenutz.storemngsim.ui.menu.category.middle

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.application.AddressSearchActivity
import com.tenutz.storemngsim.data.datasource.api.dto.category.MiddleCategoryCreateRequest
import com.tenutz.storemngsim.databinding.FragmentMiddleCategoryAddBinding
import com.tenutz.storemngsim.ui.base.BaseFragment
import com.tenutz.storemngsim.ui.menu.category.main.MainCategoryAddViewModel
import com.tenutz.storemngsim.utils.MyToast
import com.tenutz.storemngsim.utils.ext.mainActivity
import com.tenutz.storemngsim.utils.ext.navigateToMainFragment
import com.tenutz.storemngsim.utils.validation.Validator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MiddleCategoryAddFragment: BaseFragment() {

    private var _binding: FragmentMiddleCategoryAddBinding? = null
    val binding: FragmentMiddleCategoryAddBinding get() = _binding!!

    val args: MiddleCategoryAddFragmentArgs by navArgs()

    val vm: MiddleCategoryAddViewModel by viewModels()

    private val pVm: MiddleCategoriesViewModel by navGraphViewModels(R.id.navigation_middle_category) {
        defaultViewModelProviderFactory
    }

    private val startAddressSearchActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            when (it.resultCode) {
                AppCompatActivity.RESULT_OK -> {
//                    it.data?.getStringExtra("address") ?: ""
                    binding.editMiddleCategoryAddAddressDetail.setText(it.data?.getStringExtra("roadAddress") ?: "")
                }
            }
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
                    MainCategoryAddViewModel.EVENT_TOAST -> {
                        MyToast.create(mainActivity(), it.second as String, 80)?.show()
                    }
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.imageMiddleCategoryAddBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.imageMiddleCategoryAddHome.setOnClickListener {
            mainActivity().navigateToMainFragment()
        }
        binding.imageMiddleCategoryAddHamburger.setOnClickListener {
            mainActivity().binding.drawerMain.openDrawer(GravityCompat.END)
        }
        binding.btnMiddleCategoryAddSearch.setOnClickListener {
            startAddressSearchActivity.launch(
                Intent(
                    mainActivity(),
                    AddressSearchActivity::class.java
                )
            )
        }
        binding.textMiddleCategoryAddAddress.setOnClickListener {
            startAddressSearchActivity.launch(
                Intent(
                    mainActivity(),
                    AddressSearchActivity::class.java
                )
            )
        }
        binding.btnMiddleCategoryAddSave.setOnClickListener {
            Validator.validate(
                onValidation = {
                    Validator.validateCategoryName(binding.editMiddleCategoryAddName.text.toString(), true)
                    Validator.validateCategoryCode(binding.editMiddleCategoryAddCode.text.toString(), true)
                    Validator.validateBusinessNumber(binding.editMiddleCategoryAddBizNo.text.toString(), true)
                    Validator.validateRepresentative(binding.editMiddleCategoryAddOwnerName.text.toString())
                    Validator.validatePhoneNumber(binding.editMiddleCategoryAddPhone.text.toString())
                    Validator.validateAddress(binding.textMiddleCategoryAddAddress.text.toString(), binding.editMiddleCategoryAddAddressDetail.text.toString())
                    Validator.validateTid(binding.editMiddleCategoryAddTid.text.toString())
                },
                onSuccess = {
                    vm.createMiddleCategory(
                        args.mainCategoryCode,
                        MiddleCategoryCreateRequest(
                            categoryCode = binding.editMiddleCategoryAddCode.text.toString(),
                            categoryName = binding.editMiddleCategoryAddName.text.toString(),
                            use = binding.radiogroupMiddleCategoryAdd.checkedRadioButtonId == R.id.radio_middle_category_add_use,
                            businessNumber = binding.editMiddleCategoryAddBizNo.text.toString(),
                            representativeName = binding.editMiddleCategoryAddOwnerName.text.toString(),
                            tel = binding.editMiddleCategoryAddPhone.text.toString(),
                            address = binding.editMiddleCategoryAddAddressDetail.text.toString(),
                            tid = binding.editMiddleCategoryAddTid.text.toString(),
                        )
                    ) {
                        pVm.middleCategories(args.mainCategoryCode)
                    }
                },
                onFailure = { e ->
                    MyToast.create(mainActivity(), e.errorCode.message, 80)?.show()
                },
            )
        }
    }
}