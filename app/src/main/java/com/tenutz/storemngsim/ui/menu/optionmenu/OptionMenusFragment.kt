package com.tenutz.storemngsim.ui.menu.optionmenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tenutz.storemngsim.databinding.*
import com.tenutz.storemngsim.ui.menu.optiongroup.OptionGroupsFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OptionMenusFragment: Fragment() {

    private var _binding: FragmentOptionMenusBinding? = null
    val binding: FragmentOptionMenusBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOptionMenusBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textOptionMenusEdit.setOnClickListener {
            findNavController().navigate(OptionMenusFragmentDirections.actionOptionMenusFragmentToOptionMenusEditFragment())
        }
        binding.fabOptionMenusAdd.setOnClickListener {
            findNavController().navigate(OptionMenusFragmentDirections.actionOptionMenusFragmentToOptionMenuAddFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}