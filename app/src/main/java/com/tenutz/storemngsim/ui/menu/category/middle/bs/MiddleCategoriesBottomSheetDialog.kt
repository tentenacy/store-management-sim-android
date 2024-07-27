package com.tenutz.storemngsim.ui.menu.category.middle.bs

import android.os.Bundle
import android.view.View
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.databinding.BsMiddleCategoriesBinding
import com.tenutz.storemngsim.ui.base.BaseBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MiddleCategoriesBottomSheetDialog(
    private val onClickListener: (Int, Any?) -> Unit,
) : BaseBottomSheetDialogFragment<BsMiddleCategoriesBinding>(R.layout.bs_middle_categories) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.imageBsmiddleCategoriesCancel.setOnClickListener {
            dismiss()
        }
        binding.btnBsmiddleCategoriesSub.setOnClickListener {
            onClickListener(it.id, null)
            dismiss()
        }
        binding.btnBsmiddleCategoriesDetails.setOnClickListener {
            onClickListener(it.id, null)
            dismiss()
        }
    }
}