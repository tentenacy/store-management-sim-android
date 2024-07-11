package com.tenutz.storemngsim.ui.menu.optiongroup.mainmenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tenutz.storemngsim.databinding.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OgMainMenuAddFragment: Fragment() {

    private var _binding: FragmentOgMainMenuAddBinding? = null
    val binding: FragmentOgMainMenuAddBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOgMainMenuAddBinding.inflate(inflater, container, false)

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