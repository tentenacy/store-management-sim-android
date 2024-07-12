package com.tenutz.storemngsim.ui.menu.category.main.bs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.databinding.BsMainCategoriesBinding
import com.tenutz.storemngsim.ui.base.BaseBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainCategoriesBottomSheetDialog(
    private val onClickListener: (Int, Any?) -> Unit,
) : BaseBottomSheetDialogFragment<BsMainCategoriesBinding>(R.layout.bs_main_categories) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.btnBsmainCategoriesMiddle.setOnClickListener {
            onClickListener(it.id, null)
            dismiss()
        }
        binding.btnBsmainCategoriesDetails.setOnClickListener {
            onClickListener(it.id, null)
            dismiss()
        }
    }
}