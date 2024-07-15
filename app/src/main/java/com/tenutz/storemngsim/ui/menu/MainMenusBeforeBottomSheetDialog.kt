package com.tenutz.storemngsim.ui.menu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.databinding.BsMainMenusBeforeBinding
import com.tenutz.storemngsim.ui.base.BaseBottomSheetDialogFragment
import com.tenutz.storemngsim.ui.common.NumberPickerDialog
import com.tenutz.storemngsim.ui.menu.MainMenusBeforeBSDViewModel.Companion.EVENT_PICK_MAIN_CATEGORY
import com.tenutz.storemngsim.ui.menu.MainMenusBeforeBSDViewModel.Companion.EVENT_PICK_MIDDLE_CATEGORY
import com.tenutz.storemngsim.ui.menu.MainMenusBeforeBSDViewModel.Companion.EVENT_PICK_SUB_CATEGORY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainMenusBeforeBottomSheetDialog(
    private val onClickListener: (Int, Any?) -> Unit,
) : BaseBottomSheetDialogFragment<BsMainMenusBeforeBinding>(R.layout.bs_main_menus_before) {

    val vm: MainMenusBeforeBSDViewModel by viewModels()

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
                    EVENT_PICK_MAIN_CATEGORY -> {
                        showPicker(it.second as Map<String?, String?>, vm.mainCategoryCode.value) {
                            vm.setMainCategoryCode(it)
                        }
                    }
                    EVENT_PICK_MIDDLE_CATEGORY -> {
                        showPicker(it.second as Map<String?, String?>, vm.middleCategoryCode.value) {
                            vm.setMiddleCategoryCode(it)
                        }
                    }
                    EVENT_PICK_SUB_CATEGORY -> {
                        showPicker(it.second as Map<String?, String?>, vm.subCategoryCode.value) {
                            vm.setSubCategoryCode(it)
                        }
                    }
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.imageBsmainMenusBeforeCancel.setOnClickListener {
            dismiss()
        }
        binding.textBsmainMenusBeforeMainCategory.setOnClickListener {
            vm.mainCategoryPickerEvent()
        }
        binding.textBsmainMenusBeforeMiddleCategory.setOnClickListener {
            vm.middleCategoryPickerEvent()
        }
        binding.textBsmainMenusBeforeSubCategory.setOnClickListener {
            vm.subCategoryPickerEvent()
        }
        binding.btnBsmainMenusBeforeSearch.setOnClickListener { view ->
            listOf(vm.mainCategoryCode.value, vm.middleCategoryCode.value, vm.subCategoryCode.value).takeIf { it.all { it?.first != null } }?.filterNotNull()?.let {
                onClickListener(view.id, Triple(it[0].first, it[1].first, it[2].first))
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