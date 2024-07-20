package com.tenutz.storemngsim.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tenutz.storemngsim.databinding.FragmentMainBinding
import com.tenutz.storemngsim.databinding.FragmentSignupFormBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment: Fragment() {

    private var _binding: FragmentMainBinding? = null
    val binding: FragmentMainBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnMainMenuMng.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToMenuMngFragment())
        }
        binding.btnMainStatistics.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToNavigationStatistics())
        }
        binding.btnMainReviewMng.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToNavigationReview())
        }
        binding.btnMainHelp.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToHelpsFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}