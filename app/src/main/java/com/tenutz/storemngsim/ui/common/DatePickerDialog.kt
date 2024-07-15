package com.tenutz.storemngsim.ui.common

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.databinding.DlgDatePickerBinding
import com.tenutz.storemngsim.ui.base.BaseDialogFragment
import java.time.LocalDate

class DatePickerDialog(
    private val date: LocalDate? = null,
    private val onDatePickListener: (LocalDate?) -> Unit
): BaseDialogFragment<DlgDatePickerBinding>(R.layout.dlg_date_picker) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        date?.let {
            binding.datePicker.updateDate(it.year, it.monthValue - 1, it.dayOfMonth)
        }

        binding.textDlgdatePicker.setOnClickListener {
            onDatePickListener(LocalDate.of(binding.datePicker.year, binding.datePicker.month + 1, binding.datePicker.dayOfMonth))
            dismiss()
        }
        binding.textDlgdatePickerReset.setOnClickListener {
            onDatePickListener(null)
            dismiss()
        }
    }
}