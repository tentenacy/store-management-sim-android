package com.tenutz.storemngsim.ui.sales.bs

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.databinding.BsFilterBinding
import com.tenutz.storemngsim.ui.base.BaseBottomSheetDialogFragment
import com.tenutz.storemngsim.ui.common.DatePickerDialog
import com.tenutz.storemngsim.ui.sales.SalesViewModel
import com.tenutz.storemngsim.utils.ext.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SalesFilterBottomSheetDialog(
    private val onClickListener: (Int, Any?) -> Unit,
) : BaseBottomSheetDialogFragment<BsFilterBinding>(R.layout.bs_filter) {

    val pVm: SalesViewModel by viewModels({ requireParentFragment() })

    private val onPayTypeCheckedChangeListener: RadioGroup.OnCheckedChangeListener = object : RadioGroup.OnCheckedChangeListener {
        override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {

            binding.radiogroupBsfilterPayTypeL1.setOnCheckedChangeListener(null)
            binding.radiogroupBsfilterPayTypeL2.setOnCheckedChangeListener(null)

            if(group == binding.radiogroupBsfilterPayTypeL1) {
                binding.radiogroupBsfilterPayTypeL2.clearCheck()
            } else {
                binding.radiogroupBsfilterPayTypeL1.clearCheck()
            }

            binding.radiogroupBsfilterPayTypeL1.setOnCheckedChangeListener(this)
            binding.radiogroupBsfilterPayTypeL2.setOnCheckedChangeListener(this)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.pVm = pVm
        binding.lifecycleOwner = this

        expandFullHeight()
        setOnClickListeners()
        setListeners()
    }

    private fun expandFullHeight() {
        BottomSheetBehavior.from(dialog?.findViewById(com.google.android.material.R.id.design_bottom_sheet)!!).state =
            BottomSheetBehavior.STATE_EXPANDED
    }

    private fun setListeners() {
        binding.radiogroupBsfilterPayTypeL1.setOnCheckedChangeListener(
            onPayTypeCheckedChangeListener
        )
        binding.radiogroupBsfilterPayTypeL2.setOnCheckedChangeListener(
            onPayTypeCheckedChangeListener
        )
    }

    private fun setOnClickListeners() {
        binding.imageBsfilterRefresh.setOnClickListener {
            binding.radioBsfilterPayType1.isChecked = true
            binding.radioBsfilterApprovalType1.isChecked = true
            binding.radioBsfilterOrderType1.isChecked = true
            binding.textBsfilterDurationFrom.text = "0000-00-00"
            binding.textBsfilterDurationTo.text = today().end().toDateFormat()
            binding.editBsfilterLabelOrderSearch.setText("")
        }
        binding.btnBsfilterApply.setOnClickListener {
            pVm.setConditions(
                dateFrom = binding.textBsfilterDurationFrom.text.toString().takeIf { it != "0000-00-00" }?.let { dateFrom(it) },
                dateTo = binding.textBsfilterDurationTo.text.toString().takeIf { it != "0000-00-00" }?.let { dateFrom(it)?.end() },
                paymentType = when(binding.radiogroupBsfilterPayTypeL1.checkedRadioButtonId) {
                    R.id.radio_bsfilter_pay_type_2 -> "01"
                    R.id.radio_bsfilter_pay_type_3 -> "02"
                    R.id.radio_bsfilter_pay_type_4 -> "03"
                    else -> null
                } ?: if(binding.radiogroupBsfilterPayTypeL2.checkedRadioButtonId == R.id.radio_bsfilter_pay_type_5) "04" else null,
                approvalType = when(binding.radiogroupBsfilterApprovalType.checkedRadioButtonId) {
                    R.id.radio_bsfilter_approval_type_2 -> "N"
                    R.id.radio_bsfilter_approval_type_3 -> "C"
                    else -> null
                },
                orderType = when(binding.radiogroupBsfilterOrderType.checkedRadioButtonId) {
                    R.id.radio_bsfilter_order_type_2 -> "05"
                    R.id.radio_bsfilter_order_type_3 -> "06"
                    R.id.radio_bsfilter_order_type_4 -> "08"
                    else -> null
                },
                binding.editBsfilterLabelOrderSearch.text.toString(),
            )
            pVm.salesList()
            dismiss()
        }
        binding.constraintBsfilterDurationFromContainer.setOnClickListener {
            DatePickerDialog(
                localDateFrom(binding.textBsfilterDurationFrom.text.toString()),
                onDatePickListener = { date ->
                    binding.textBsfilterDurationFrom.text = date.toDateFormat().takeIf { it.isNotBlank() } ?: "0000-00-00"
                }
            ).show(childFragmentManager, "datePickerDialog")
        }
        binding.constraintBsfilterDurationToContainer.setOnClickListener {
            DatePickerDialog(
                localDateFrom(binding.textBsfilterDurationTo.text.toString()),
                onDatePickListener = { date ->
                    binding.textBsfilterDurationTo.text = date.toDateFormat().takeIf { it.isNotBlank() } ?: today().end().toDateFormat()
                }
            ).show(childFragmentManager, "datePickerDialog")
        }
    }
}