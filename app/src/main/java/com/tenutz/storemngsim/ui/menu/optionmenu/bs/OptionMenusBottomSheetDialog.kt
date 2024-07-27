package com.tenutz.storemngsim.ui.menu.optionmenu.bs

import android.os.Bundle
import android.view.View
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.databinding.BsOptionMenusBinding
import com.tenutz.storemngsim.ui.base.BaseBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OptionMenusBottomSheetDialog(
    private val onClickListener: (Int, Any?) -> Unit,
) : BaseBottomSheetDialogFragment<BsOptionMenusBinding>(R.layout.bs_option_menus) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.imageBsoptionMenusCancel.setOnClickListener {
            dismiss()
        }
        binding.btnBsoptionMenusOptionGroup.setOnClickListener {
            onClickListener(it.id, null)
            dismiss()
        }
        binding.btnBsoptionMenusDetails.setOnClickListener {
            onClickListener(it.id, null)
            dismiss()
        }
    }
}