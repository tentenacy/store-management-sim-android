package com.tenutz.storemngsim.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.databinding.FragmentMainBinding
import com.tenutz.storemngsim.databinding.FragmentSignupFormBinding
import com.tenutz.storemngsim.utils.ext.mainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment: Fragment() {

    private var _binding: FragmentMainBinding? = null
    val binding: FragmentMainBinding get() = _binding!!

    val vm: MainViewModel by navGraphViewModels(R.id.navigation_main_xml) {
        defaultViewModelProviderFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.vm = vm
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setOnClickListeners()
    }

    private fun initViews() {
        vm.storeMain()
    }

    private fun setOnClickListeners() {
        binding.btnMainMenuMng.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToMenuMngFragment())
        }
        binding.btnMainStatistics.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToNavigationStatistics())
        }
        binding.btnMainReviewMng.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToNavigationReview())
        }
        binding.btnMainSales.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToSalesFragment())
        }
        binding.btnMainHelp.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToHelpsFragment())
        }
        binding.btnMainLogoutForTest.setOnClickListener {
            mainActivity().vm.logout()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}