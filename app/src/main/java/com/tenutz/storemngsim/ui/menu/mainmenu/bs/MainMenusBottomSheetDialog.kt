package com.tenutz.storemngsim.ui.menu.mainmenu.bs

import android.os.Bundle
import android.view.View
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.databinding.BsMainMenusBinding
import com.tenutz.storemngsim.ui.base.BaseBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainMenusBottomSheetDialog(
    private val onClickListener: (Int, Any?) -> Unit,
) : BaseBottomSheetDialogFragment<BsMainMenusBinding>(R.layout.bs_main_menus) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.imageBsmainMenusCancel.setOnClickListener {
            dismiss()
        }
        binding.btnBsmainMenusOptionGroup.setOnClickListener {
            onClickListener(it.id, null)
            dismiss()
        }
        binding.btnBsmainMenusDetails.setOnClickListener {
            onClickListener(it.id, null)
            dismiss()
        }
    }
}