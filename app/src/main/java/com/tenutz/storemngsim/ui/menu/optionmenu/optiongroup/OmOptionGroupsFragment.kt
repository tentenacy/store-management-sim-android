package com.tenutz.storemngsim.ui.menu.optionmenu.optiongroup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tenutz.storemngsim.databinding.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OmOptionGroupsFragment: Fragment() {

    private var _binding: FragmentOmOptionGroupsBinding? = null
    val binding: FragmentOmOptionGroupsBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOmOptionGroupsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}