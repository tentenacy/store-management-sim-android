package com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.mainmenu.bs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.databinding.BsMainCategoriesBinding
import com.tenutz.storemngsim.databinding.BsMainMenusBinding
import com.tenutz.storemngsim.databinding.BsMiddleCategoriesBinding
import com.tenutz.storemngsim.databinding.BsOgMainMenuAddBeforeBinding
import com.tenutz.storemngsim.ui.base.BaseBottomSheetDialogFragment
import com.tenutz.storemngsim.ui.common.NumberPickerDialog
import com.tenutz.storemngsim.ui.menu.MainMenusBeforeBSDViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OgMainMenuAddBeforeBottomSheetDialog(
    private val onClickListener: (Int, Any?) -> Unit,
) : BaseBottomSheetDialogFragment<BsOgMainMenuAddBeforeBinding>(R.layout.bs_og_main_menu_add_before) {

    val vm: OgMainMenuAddBeforeBSDViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = vm

        setOnClickListeners()
        observeData()
    }

    private fun observeData() {
        vm.viewEvent.observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                when (it.first) {
                    MainMenusBeforeBSDViewModel.EVENT_PICK_MAIN_CATEGORY -> {
                        showPicker(it.second as Map<String?, String?>, vm.mainCategoryCode.value) {
                            vm.setMainCategoryCode(it)
                        }
                    }
                    MainMenusBeforeBSDViewModel.EVENT_PICK_MIDDLE_CATEGORY -> {
                        showPicker(it.second as Map<String?, String?>, vm.middleCategoryCode.value) {
                            vm.setMiddleCategoryCode(it)
                        }
                    }
                    MainMenusBeforeBSDViewModel.EVENT_PICK_SUB_CATEGORY -> {
                        showPicker(it.second as Map<String?, String?>, vm.subCategoryCode.value) {
                            vm.setSubCategoryCode(it)
                        }
                    }
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.imageBsogMainMenuAddBeforeCancel.setOnClickListener {
            dismiss()
        }
        binding.textBsogMainMenuAddBeforeMainCategory.setOnClickListener {
            vm.mainCategoryPickerEvent()
        }
        binding.textBsogMainMenuAddBeforeMiddleCategory.setOnClickListener {
            vm.middleCategoryPickerEvent()
        }
        binding.textBsogMainMenuAddBeforeSubCategory.setOnClickListener {
            vm.subCategoryPickerEvent()
        }
        binding.btnBsogMainMenuAddBeforeSearch.setOnClickListener { view ->
            vm.mainCategoryCode.value?.first?.let {
                onClickListener(view.id, Triple(it, vm.middleCategoryCode.value?.first, vm.subCategoryCode.value?.first))
                dismiss()
            }
        }
    }

    private fun showPicker(map: Map<String?, String?>, currentCode: Pair<String?, String?>?, onPickedListener: (Pair<String?, String?>) -> Unit) {
        map.toList().takeIf { it.isNotEmpty() }?.let { values ->
            NumberPickerDialog(
                values = values.map { it.second },
                pickedValue = currentCode?.let { code -> values.indexOfFirst { it.first == code.first } + 1 } ?: 1,
                onClickListener = { id, value ->
                    when (id) {
                        R.id.text_dlgnumber_picker -> {
                            onPickedListener(values[value as Int])
                        }
                    }
                }
            ).show(childFragmentManager, "numberPickerDialog")
        }
    }
}