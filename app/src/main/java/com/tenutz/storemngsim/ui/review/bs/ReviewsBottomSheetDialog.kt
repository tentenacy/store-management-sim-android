package com.tenutz.storemngsim.ui.review.bs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.databinding.*
import com.tenutz.storemngsim.ui.base.BaseBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewsBottomSheetDialog(
    private val onClickListener: (Int, Any?) -> Unit,
) : BaseBottomSheetDialogFragment<BsReviewsBinding>(R.layout.bs_reviews) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.btnBsreviewsDelete.setOnClickListener {
            onClickListener(it.id, null)
            dismiss()
        }
        binding.btnBsreviewsCancel.setOnClickListener {
            dismiss()
        }
    }
}