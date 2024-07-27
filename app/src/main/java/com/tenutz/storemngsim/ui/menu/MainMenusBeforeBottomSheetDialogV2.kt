package com.tenutz.storemngsim.ui.menu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.databinding.BsMainMenusBeforeV2Binding
import com.tenutz.storemngsim.ui.base.BaseBottomSheetDialogFragment
import com.tenutz.storemngsim.ui.common.NumberPickerDialog
import com.tenutz.storemngsim.ui.menu.MainMenusBeforeBSDViewModel.Companion.EVENT_PICK_SUB_CATEGORY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainMenusBeforeBottomSheetDialogV2(
    private val onClickListener: (Int, Pair<String, String>) -> Unit,
) : BaseBottomSheetDialogFragment<BsMainMenusBeforeV2Binding>(R.layout.bs_main_menus_before_v2) {
    val vm: MainMenusBeforeBSDViewModelV2 by viewModels()

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
        binding.imageBsmainMenusBeforeV2Cancel.setOnClickListener {
            dismiss()
        }
        binding.textBsmainMenusBeforeV2SubCategory.setOnClickListener {
            vm.subCategoryPickerEvent()
        }
        binding.btnBsmainMenusBeforeV2Search.setOnClickListener { view ->
            vm.subCategoryCode.value.takeIf { it != null }?.let {
                onClickListener(view.id, Pair(it.first!!, it.second!!))
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