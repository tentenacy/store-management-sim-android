package com.tenutz.storemngsim.ui.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tenutz.storemngsim.databinding.FragmentMainBinding
import com.tenutz.storemngsim.databinding.FragmentReviewsBinding
import com.tenutz.storemngsim.databinding.FragmentSignupFormBinding
import com.tenutz.storemngsim.databinding.FragmentStatisticsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticsFragment: Fragment() {

    private var _binding: FragmentStatisticsBinding? = null
    val binding: FragmentStatisticsBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)

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