package com.tenutz.storemngsim.ui.menu.mainmenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tenutz.storemngsim.databinding.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainMenusFragment: Fragment() {

    private var _binding: FragmentMainMenusBinding? = null
    val binding: FragmentMainMenusBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainMenusBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textMainMenusEdit.setOnClickListener {
            findNavController().navigate(MainMenusFragmentDirections.actionMainMenusFragmentToMainMenusEditFragment())
        }
        binding.fabMainMenusAdd.setOnClickListener {
            findNavController().navigate(MainMenusFragmentDirections.actionMainMenusFragmentToMainMenuAddFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}