package com.tenutz.storemngsim.ui.menu.optiongroup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tenutz.storemngsim.databinding.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OgMappingMenusFragment: Fragment() {

    private var _binding: FragmentOgMappingMenusBinding? = null
    val binding: FragmentOgMappingMenusBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOgMappingMenusBinding.inflate(inflater, container, false)

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