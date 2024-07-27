package com.tenutz.storemngsim.ui.menu.optiongroup.bs

import android.os.Bundle
import android.view.View
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.databinding.BsOptionGroupsBinding
import com.tenutz.storemngsim.ui.base.BaseBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OptionGroupsBottomSheetDialog(
    private val onClickListener: (Int, Any?) -> Unit,
) : BaseBottomSheetDialogFragment<BsOptionGroupsBinding>(R.layout.bs_option_groups) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.imageBsoptionGroupsCancel.setOnClickListener {
            dismiss()
        }
        binding.btnBsoptionGroupsMappingMenus.setOnClickListener {
            onClickListener(it.id, null)
            dismiss()
        }
        binding.btnBsoptionGroupsDetails.setOnClickListener {
            onClickListener(it.id, null)
            dismiss()
        }
    }
}