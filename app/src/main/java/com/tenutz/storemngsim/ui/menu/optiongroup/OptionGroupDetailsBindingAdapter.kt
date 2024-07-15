package com.tenutz.storemngsim.ui.menu.optiongroup

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter

object OptionGroupDetailsBindingAdapter {

    @SuppressLint("ClickableViewAccessibility")
    @BindingAdapter("bind:switchEnabled")
    @JvmStatic
    fun setSwitchEnabled(switch: com.suke.widget.SwitchButton, enabled: Boolean?) {
        switch.setOnTouchListener { v, event -> enabled != true }
    }

    fun enableDisableView(view: View, enabled: Boolean) {
        view.isEnabled = enabled
        if (view is ViewGroup) {
            val group = view
            for (idx in 0 until group.childCount) {
                enableDisableView(group.getChildAt(idx), enabled)
            }
        }
    }

    @BindingAdapter("bind:switchChecked")
    @JvmStatic
    fun setSwitchChecked(switch: com.suke.widget.SwitchButton, checked: Boolean?) {
        switch.setChecked(checked == true)
    }
}