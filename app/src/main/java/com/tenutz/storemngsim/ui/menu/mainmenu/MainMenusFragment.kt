package com.tenutz.storemngsim.ui.menu.mainmenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.databinding.*
import com.tenutz.storemngsim.ui.menu.mainmenu.args.MainMenusNavArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainMenusFragment: Fragment() {

    private var _binding: FragmentMainMenusBinding? = null
    val binding: FragmentMainMenusBinding get() = _binding!!

    lateinit var args: MainMenusNavArgs

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainMenusBinding.inflate(inflater, container, false)

        requireArguments().apply {
            args = MainMenusNavArgs(
                getString("mainCategoryCode")!!,
                getString("middleCategoryCode")!!,
                getString("subCategoryCode")!!,
            )
        }

        Logger.i(args.toString())

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