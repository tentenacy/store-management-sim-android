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
import com.tenutz.storemngsim.data.datasource.api.dto.category.MiddleCategoryUpdateRequest
import com.tenutz.storemngsim.databinding.FragmentMiddleCategoryDetailsBinding
import com.tenutz.storemngsim.ui.base.BaseFragment
import com.tenutz.storemngsim.utils.MyToast
import com.tenutz.storemngsim.utils.ext.mainActivity
import com.tenutz.storemngsim.utils.ext.navigateToMainFragment
import com.tenutz.storemngsim.utils.validation.Validator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MiddleCategoryDetailsFragment: BaseFragment() {

    private var _binding: FragmentMiddleCategoryDetailsBinding? = null
    val binding: FragmentMiddleCategoryDetailsBinding get() = _binding!!

    val args: MiddleCategoryDetailsFragmentArgs by navArgs()

    val vm: MiddleCategoryDetailsViewModel by viewModels()

    private val pVm: MiddleCategoriesViewModel by navGraphViewModels(R.id.navigation_middle_category) {
        defaultViewModelProviderFactory
    }

    private val startAddressSearchActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            when (it.resultCode) {
                AppCompatActivity.RESULT_OK -> {
//                    it.data?.getStringExtra("address") ?: ""
                    binding.editMiddleCategoryDetailsAddressDetail.setText(it.data?.getStringExtra("roadAddress") ?: "")
                }
            }
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
        binding.imageMiddleCategoryDetailsBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.imageMiddleCategoryDetailsHome.setOnClickListener {
            mainActivity().navigateToMainFragment()
        }
        binding.imageMiddleCategoryDetailsHamburger.setOnClickListener {
            mainActivity().binding.drawerMain.openDrawer(GravityCompat.END)
        }
        binding.btnMiddleCategoryDetailsCancel.setOnClickListener {
            binding.vm = vm
            vm.switchToReadMode()
        }
        binding.btnMiddleCategoryDetailsSearch.setOnClickListener {
            startAddressSearchActivity.launch(
                Intent(
                    mainActivity(),
                    AddressSearchActivity::class.java
                )
            )
        }
        binding.textMiddleCategoryDetailsAddress.setOnClickListener {
            startAddressSearchActivity.launch(
                Intent(
                    mainActivity(),
                    AddressSearchActivity::class.java
                )
            )
        }
        binding.btnMiddleCategoryDetailsSave.setOnClickListener {
            Validator.validate(
                onValidation = {
                    Validator.validateCategoryName(binding.editMiddleCategoryDetailsName.text.toString(), true)
                    Validator.validateBusinessNumber(binding.editMiddleCategoryDetailsBizNo.text.toString(), true)
                    Validator.validateRepresentative(binding.editMiddleCategoryDetailsOwnerName.text.toString())
                    Validator.validatePhoneNumber(binding.editMiddleCategoryDetailsPhone.text.toString())
                    Validator.validateAddress(binding.textMiddleCategoryDetailsAddress.text.toString(), binding.editMiddleCategoryDetailsAddressDetail.text.toString())
                    Validator.validateTid(binding.editMiddleCategoryDetailsTid.text.toString())
                },
                onSuccess = {
                    vm.updateMiddleCategory(
                        args.mainCategoryCode,
                        args.middleCategoryCode,
                        MiddleCategoryUpdateRequest(
                            categoryName = binding.editMiddleCategoryDetailsName.text.toString(),
                            use = binding.radiogroupMiddleCategoryDetails.checkedRadioButtonId == R.id.radio_middle_category_details_use,
                            businessNumber = binding.editMiddleCategoryDetailsBizNo.text.toString(),
                            representativeName = binding.editMiddleCategoryDetailsOwnerName.text.toString(),
                            tel = binding.editMiddleCategoryDetailsPhone.text.toString(),
                            address = binding.editMiddleCategoryDetailsAddressDetail.text.toString(),
                            tid = binding.editMiddleCategoryDetailsTid.text.toString(),
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}