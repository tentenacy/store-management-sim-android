package com.tenutz.storemngsim.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

abstract class NumberTextWatcher(private val editText: EditText): TextWatcher {
    abstract val MAX_NUM: Int
    abstract val FORMAT_NUM: String

    override fun beforeTextChanged(
        s: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        s?.let {
            if (it.trimStart('0').isBlank()) {
                editText.removeTextChangedListener(this)
                editText.setText("")
                editText.addTextChangedListener(this)
                return@let
            }
        }
    }

    override fun afterTextChanged(s: Editable?) {
    }
}