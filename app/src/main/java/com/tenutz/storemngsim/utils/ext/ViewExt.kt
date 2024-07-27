package com.tenutz.storemngsim.utils.ext

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

fun FragmentManager.dismissAllDialogs() {
    fragments.forEach { fragment ->
        (fragment as? DialogFragment)?.dismissAllowingStateLoss()
        fragment.childFragmentManager.dismissAllDialogs()
    }
}