package com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.mainmenu.bs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.databinding.BsOgMainMenuAddBeforeV2Binding
import com.tenutz.storemngsim.ui.base.BaseBottomSheetDialogFragment
import com.tenutz.storemngsim.ui.common.NumberPickerDialog
import com.tenutz.storemngsim.ui.menu.MainMenusBeforeBSDViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OgMainMenuAddBeforeBottomSheetDialogV2(
    private val onClickListener: (Int, String?) -> Unit,
) : BaseBottomSheetDialogFragment<BsOgMainMenuAddBeforeV2Binding>(R.layout.bs_og_main_menu_add_before_v2) {

    val vm: OgMainMenuAddBeforeBSDViewModelV2 by viewModels()

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
        binding.imageBsogMainMenuAddBeforeV2Cancel.setOnClickListener {
            dismiss()
        }
        binding.textBsogMainMenuAddBeforeV2SubCategory.setOnClickListener {
            vm.subCategoryPickerEvent()
        }
        binding.btnBsogMainMenuAddBeforeV2Search.setOnClickListener { view ->
            onClickListener(view.id, vm.subCategoryCode.value?.first)
            dismiss()
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