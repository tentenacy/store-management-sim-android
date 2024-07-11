package com.tenutz.storemngsim.ui.review.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tenutz.storemngsim.databinding.FragmentMainBinding
import com.tenutz.storemngsim.databinding.FragmentReviewsBinding
import com.tenutz.storemngsim.databinding.FragmentSignupFormBinding
import com.tenutz.storemngsim.databinding.FragmentStoreReplyPostBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoreReplyPostFragment: Fragment() {

    private var _binding: FragmentStoreReplyPostBinding? = null
    val binding: FragmentStoreReplyPostBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentStoreReplyPostBinding.inflate(inflater, container, false)

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