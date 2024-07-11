package com.tenutz.storemngsim.ui.menu.category.sub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tenutz.storemngsim.databinding.FragmentMainCategoriesBinding
import com.tenutz.storemngsim.databinding.FragmentMenuMngBinding
import com.tenutz.storemngsim.databinding.FragmentMiddleCategoriesBinding
import com.tenutz.storemngsim.databinding.FragmentSubCategoriesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubCategoriesFragment: Fragment() {

    private var _binding: FragmentSubCategoriesBinding? = null
    val binding: FragmentSubCategoriesBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSubCategoriesBinding.inflate(inflater, container, false)

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