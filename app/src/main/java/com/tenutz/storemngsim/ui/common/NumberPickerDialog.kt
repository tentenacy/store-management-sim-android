package com.tenutz.storemngsim.ui.common

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.databinding.DlgNumberPickerBinding
import com.tenutz.storemngsim.ui.base.BaseDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NumberPickerDialog(
    private val values: List<Pair<String?, String?>>,
    private val pickedValue: Int = 1,
    private val onClickListener: (Int, Any?) -> Unit,
) : BaseDialogFragment<DlgNumberPickerBinding>(R.layout.dlg_number_picker) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.numberPicker.minValue = 1
        binding.numberPicker.maxValue = values.size

        binding.numberPicker.displayedValues = values.map { it.second }.toTypedArray()
        binding.numberPicker.value = pickedValue

        binding.textDlgnumberPicker.setOnClickListener {
            onClickListener(it.id, binding.numberPicker.value - 1)
            dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}