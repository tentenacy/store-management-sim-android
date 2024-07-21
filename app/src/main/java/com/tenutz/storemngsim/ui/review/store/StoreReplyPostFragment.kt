package com.tenutz.storemngsim.ui.review.store

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
import com.tenutz.storemngsim.ui.review.store.StoreReplyPostFragmentArgs
import com.tenutz.storemngsim.ui.review.store.StoreReplyPostViewModel
import com.tenutz.storemngsim.ui.review.store.StoreReviewsViewModel
import com.tenutz.storemngsim.utils.MyToast
import com.tenutz.storemngsim.utils.ext.mainActivity
import com.tenutz.storemngsim.utils.validation.Validator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoreReplyPostFragment: Fragment() {

    private var _binding: FragmentStoreReplyPostBinding? = null
    val binding: FragmentStoreReplyPostBinding get() = _binding!!

    val args: StoreReplyPostFragmentArgs by navArgs()

    val vm: StoreReplyPostViewModel by viewModels()

    private val pVm: StoreReviewsViewModel by navGraphViewModels(R.id.navigation_review) {
        defaultViewModelProviderFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.content.value = args.storeReview.storeReviewReply?.content
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentStoreReplyPostBinding.inflate(inflater, container, false)

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
                    StoreReplyPostViewModel.EVENT_NAVIGATE_UP -> {
                        findNavController().navigateUp()
                    }
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.imageStoreReplyPostCancel.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnStoreReplyPostSave.setOnClickListener {
            Validator.validate(
                onValidation = {
                    Validator.validateReviewReplyContent(binding.editStoreReplyPostContent.text.toString(), true)
                },
                onSuccess = {
                    args.storeReview.storeReviewReply?.let {
                        vm.updateStoreReviewReply(
                            it.seq,
                            ReviewReplyUpdateRequest(binding.editStoreReplyPostContent.text.toString()),
                        ) {
                            pVm.storeReviews()
                        }
                    } ?: kotlin.run {
                        vm.createStoreReviewReply(
                            args.storeReview.seq,
                            ReviewReplyCreateRequest(binding.editStoreReplyPostContent.text.toString())
                        ) {
                            pVm.storeReviews()
                        }
                    }
                },
                onFailure = { e ->
                    MyToast.create(mainActivity(), e.errorCode.message, 80)?.show()
                },
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}