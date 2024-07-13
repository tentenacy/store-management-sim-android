package com.tenutz.storemngsim.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tenutz.storemngsim.R


abstract class BaseDialogFragment<VB : ViewDataBinding>(private val layoutId: Int) :
    DialogFragment() {

    private var _binding: ViewDataBinding? = null

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = _binding!! as VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate<VB>(inflater, layoutId, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}