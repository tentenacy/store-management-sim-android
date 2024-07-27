package com.tenutz.storemngsim.utils

import android.widget.EditText
import kotlin.math.min

class HourTextWatcher(private val editText: EditText): NumberTextWatcher(editText) {

    override val MAX_NUM: Int
        get() = 23

    override val FORMAT_NUM: String
        get() = "%1$02d"

    override fun onTextChanged(
        s: CharSequence?,
        start: Int,
        before: Int,
        count: Int
    ) {
        super.onTextChanged(s, start, before, count)
        s?.let {
            if (
                editText.isFocusable &&
                """[0-9]+""".toRegex().matches(it.trimStart('0'))
            ) {
                editText.removeTextChangedListener(this)
                editText.setText(String.format(FORMAT_NUM, min("${it.trimStart('0')}".toInt(), MAX_NUM)))
                editText.setSelection(editText.length())
                editText.addTextChangedListener(this)
            }
        }
    }
}