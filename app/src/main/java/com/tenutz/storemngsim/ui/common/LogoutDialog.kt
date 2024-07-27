package com.tenutz.storemngsim.ui.common

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.databinding.DlgLogoutBinding
import com.tenutz.storemngsim.ui.base.BaseDialogFragment

class LogoutDialog(
    private val logout: () -> Unit
): BaseDialogFragment<DlgLogoutBinding>(R.layout.dlg_logout) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textDlglogoutCancel.setOnClickListener {
            dismiss()
        }
        binding.textDlglogoutLogout.setOnClickListener {
            logout()
            dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}