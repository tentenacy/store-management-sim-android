package com.tenutz.storemngsim.ui.review.menu

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
import com.tenutz.storemngsim.data.datasource.api.dto.store.ReviewReplyCreateRequest
import com.tenutz.storemngsim.data.datasource.api.dto.store.ReviewReplyUpdateRequest
import com.tenutz.storemngsim.databinding.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuReplyPostFragment: Fragment() {

    private var _binding: FragmentMenuReplyPostBinding? = null
    val binding: FragmentMenuReplyPostBinding get() = _binding!!

    val args: MenuReplyPostFragmentArgs by navArgs()

    val vm: MenuReplyPostViewModel by viewModels()

    private val pVm: MenuReviewsViewModel by navGraphViewModels(R.id.navigation_review) {
        defaultViewModelProviderFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.content.value = args.menuReview.menuReviewReply?.content
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMenuReplyPostBinding.inflate(inflater, container, false)

        binding.vm = vm
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
        observeData()
    }

    private fun observeData() {
        vm.viewEvent.observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                when (it.first) {
                    MenuReplyPostViewModel.EVENT_NAVIGATE_UP -> {
                        findNavController().navigateUp()
                    }
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.btnMenuReplyPostSave.setOnClickListener {
            args.menuReview.menuReviewReply?.let {
                vm.updateMenuReviewReply(
                    it.seq,
                    ReviewReplyUpdateRequest(binding.editMenuReplyPostContent.text.toString()),
                ) {
                    pVm.menuReviews()
                }
            } ?: kotlin.run {
                vm.createMenuReviewReply(
                    args.menuReview.seq,
                    ReviewReplyCreateRequest(binding.editMenuReplyPostContent.text.toString())
                ) {
                    pVm.menuReviews()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}