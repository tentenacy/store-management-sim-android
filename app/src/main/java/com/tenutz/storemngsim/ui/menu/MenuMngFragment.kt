package com.tenutz.storemngsim.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tenutz.storemngsim.databinding.FragmentMenuMngBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuMngFragment: Fragment() {

    private var _binding: FragmentMenuMngBinding? = null
    val binding: FragmentMenuMngBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMenuMngBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnMenuMngCategory.setOnClickListener {
            findNavController().navigate(MenuMngFragmentDirections.actionMenuMngFragmentToNavigationMainCategory())
        }
        binding.btnMenuMngMainMenu.setOnClickListener {
            findNavController().navigate(MenuMngFragmentDirections.actionMenuMngFragmentToMainMenusFragment())
        }
        binding.btnMenuMngOptionMenu.setOnClickListener {
            findNavController().navigate(MenuMngFragmentDirections.actionMenuMngFragmentToOptionMenusFragment())
        }
        binding.btnMenuMngOptionGroup.setOnClickListener {
            findNavController().navigate(MenuMngFragmentDirections.actionMenuMngFragmentToOptionGroupsFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}