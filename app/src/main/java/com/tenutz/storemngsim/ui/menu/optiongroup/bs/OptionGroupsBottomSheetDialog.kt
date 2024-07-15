package com.tenutz.storemngsim.ui.menu.optiongroup.bs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.databinding.BsMainCategoriesBinding
import com.tenutz.storemngsim.databinding.BsMainMenusBinding
import com.tenutz.storemngsim.databinding.BsMiddleCategoriesBinding
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