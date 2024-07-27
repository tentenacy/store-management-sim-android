package com.tenutz.storemngsim.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class BusinessNumberTextWatcher(private val editText: EditText): TextWatcher {

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(
        s: CharSequence?,
        start: Int,
        before: Int,
        count: Int
    ) {
        s?.let {
            if (
                editText.isFocusable &&
                """[0-9-]+""".toRegex().matches(it)
            ) {
                editText.removeTextChangedListener(this)
                if(before > count) {
                    if(start == 4 || start == 7) {
                        editText.setText(it.dropLast(1))
                    }
                } else {
                    if(start == 4-1 || start == 7-1) {
                        editText.setText("${it.dropLast(1)}-${it.last()}")
                    } else if(s.startsWith("-")) {
                        editText.setText("")
                    }
                }
                editText.setSelection(editText.length())
                editText.addTextChangedListener(this)
            }
        }
    }

    override fun afterTextChanged(s: Editable?) {
    }
}