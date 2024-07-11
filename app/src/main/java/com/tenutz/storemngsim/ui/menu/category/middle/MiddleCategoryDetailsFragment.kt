package com.tenutz.storemngsim.ui.menu.category.middle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tenutz.storemngsim.databinding.FragmentMainCategoriesBinding
import com.tenutz.storemngsim.databinding.FragmentMenuMngBinding
import com.tenutz.storemngsim.databinding.FragmentMiddleCategoriesBinding
import com.tenutz.storemngsim.databinding.FragmentMiddleCategoryDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MiddleCategoryDetailsFragment: Fragment() {

    private var _binding: FragmentMiddleCategoryDetailsBinding? = null
    val binding: FragmentMiddleCategoryDetailsBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMiddleCategoryDetailsBinding.inflate(inflater, container, false)

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