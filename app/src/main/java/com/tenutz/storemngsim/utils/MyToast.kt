package com.tenutz.storemngsim.utils

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.databinding.ToastBinding
import com.tenutz.storemngsim.utils.ext.toPx

object MyToast {

    fun create(context: Context, message: String, yOffset: Int? = null): Toast? {
        val inflater = LayoutInflater.from(context)
        val binding: ToastBinding =
            DataBindingUtil.inflate(inflater, R.layout.toast, null, false)

        binding.textToastContent.text = message

        return Toast(context).apply {
            setGravity(Gravity.BOTTOM or Gravity.CENTER, 0, yOffset?.toPx?.toInt() ?: 16.toPx.toInt())
            duration = Toast.LENGTH_SHORT
            view = binding.root
        }
    }
}