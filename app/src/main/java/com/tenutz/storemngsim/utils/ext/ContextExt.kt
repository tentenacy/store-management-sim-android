package com.tenutz.storemngsim.utils.ext

import androidx.fragment.app.Fragment
import com.tenutz.storemngsim.application.MainActivity

fun Fragment.mainActivity() = (requireActivity() as MainActivity)