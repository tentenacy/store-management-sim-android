package com.tenutz.storemngsim.ui.menu.optiongroup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tenutz.storemngsim.databinding.*
import com.tenutz.storemngsim.ui.menu.mainmenu.MainMenusFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OptionGroupsFragment: Fragment() {

    private var _binding: FragmentOptionGroupsBinding? = null
    val binding: FragmentOptionGroupsBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOptionGroupsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textOptionGroupsEdit.setOnClickListener {
            findNavController().navigate(OptionGroupsFragmentDirections.actionOptionGroupsFragmentToOptionGroupsEditFragment())
        }
        binding.fabOptionGroupsAdd.setOnClickListener {
            findNavController().navigate(OptionGroupsFragmentDirections.actionOptionGroupsFragmentToOptionGroupAddFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}