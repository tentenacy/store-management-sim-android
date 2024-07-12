package com.tenutz.storemngsim.ui.menu.category.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tenutz.storemngsim.databinding.FragmentMainCategoriesBinding
import com.tenutz.storemngsim.databinding.FragmentMenuMngBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainCategoriesFragment: Fragment() {

    private var _binding: FragmentMainCategoriesBinding? = null
    val binding: FragmentMainCategoriesBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainCategoriesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textMainCategoriesEdit.setOnClickListener {
            findNavController().navigate(MainCategoriesFragmentDirections.actionMainCategoriesFragmentToMainCategoriesEditFragment())
        }
        binding.fabMainCategoriesAdd.setOnClickListener {
            findNavController().navigate(MainCategoriesFragmentDirections.actionMainCategoriesFragmentToMainCategoryAddFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}