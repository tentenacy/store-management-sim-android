package com.tenutz.storemngsim.ui.review.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.data.datasource.paging.entity.StoreReviews
import com.tenutz.storemngsim.databinding.TabStoreReviewsBinding
import com.tenutz.storemngsim.ui.base.BaseFragment
import com.tenutz.storemngsim.ui.review.ReviewsFragmentDirections
import com.tenutz.storemngsim.ui.review.ReviewsViewModel
import com.tenutz.storemngsim.ui.review.bs.ReviewsBottomSheetDialog
import com.tenutz.storemngsim.ui.review.store.args.StoreReplyPostNavArgs
import com.tenutz.storemngsim.utils.ext.editTextObservable
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class StoreReviewsTabFragment: BaseFragment() {

    private val disposable = CompositeDisposable()

    private var _binding: TabStoreReviewsBinding? = null
    val binding: TabStoreReviewsBinding get() = _binding!!

    val vm: StoreReviewsViewModel by navGraphViewModels(R.id.navigation_review) {
        defaultViewModelProviderFactory
    }

    private val pVm: ReviewsViewModel by navGraphViewModels(R.id.navigation_review) {
        defaultViewModelProviderFactory
    }

    private val adapter: StoreReviewsAdapter by lazy {
        StoreReviewsAdapter { id, item ->
            when(id) {
                R.id.text_itstore_reviews_reply_post -> {
                    val storeReview = item as StoreReviews.StoreReview
                    findNavController().navigate(
                        ReviewsFragmentDirections.actionReviewsFragmentToStoreReplyPostFragment(
                        StoreReplyPostNavArgs(
                            seq = storeReview.seq,
                            siteCode = storeReview.siteCode,
                            storeCode = storeReview.storeCode,
                            middleCategoryCode = storeReview.middleCategoryCode,
                            middleCategoryName = storeReview.middleCategoryName,
                            createdBy = storeReview.createdBy,
                            createdAt = storeReview.createdAt,
                            content = storeReview.content,
                            keyword = storeReview.keyword,
                            level = storeReview.level,
                            rating = storeReview.rating,
                            sno = storeReview.sno,
                        )
                    ))
                }
                R.id.text_itstore_reviews_reply_edit -> {
                    val storeReview = item as StoreReviews.StoreReview
                    findNavController().navigate(
                        ReviewsFragmentDirections.actionReviewsFragmentToStoreReplyPostFragment(
                        StoreReplyPostNavArgs(
                            seq = storeReview.seq,
                            siteCode = storeReview.siteCode,
                            storeCode = storeReview.storeCode,
                            middleCategoryCode = storeReview.middleCategoryCode,
                            middleCategoryName = storeReview.middleCategoryName,
                            createdBy = storeReview.createdBy,
                            createdAt = storeReview.createdAt,
                            content = storeReview.content,
                            keyword = storeReview.keyword,
                            level = storeReview.level,
                            rating = storeReview.rating,
                            sno = storeReview.sno,
                            storeReviewReply = storeReview.storeReviewReply,
                        )
                    ))
                }
                R.id.text_itstore_reviews_reply_delete -> {
                    val replySeq = item as Long
                    ReviewsBottomSheetDialog { id2, _ ->
                        when(id2) {
                            R.id.btn_bsreviews_delete -> {
                                vm.deleteStoreReviewReply(replySeq) {
                                    vm.storeReviews()
                                }
                            }
                        }
                    }.show(childFragmentManager, "reviewsBottomSheetDialog")
                }
            }
        }.apply {
            addLoadStateListener { loadState ->
                vm.empty.value =
                    !(loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && adapter.itemCount < 1)
            }
        }
    }

    var dividerItemDecoration: DividerItemDecoration? = null
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.storeReviews()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = TabStoreReviewsBinding.inflate(inflater, container, false)

        binding.vm = vm
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observeData()
    }

    private fun initViews() {
        binding.recyclerTstoreReviews.adapter = adapter
        dividerItemDecoration?.let { binding.recyclerTstoreReviews.addItemDecoration(it) }
        editTextObservable(binding.editTstoreReviewsSearch)
            .debounce(500, TimeUnit.MILLISECONDS).skip(1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                vm.storeReviews(it)
            }
            .addTo(disposable)
    }

    private fun observeData() {
        vm.queryType.observe(viewLifecycleOwner) {
            it.first?.let {
                binding.radiogroupTstoreReviews.setOnCheckedChangeListener(null)
                binding.radiogroupTstoreReviews.check(it)
                binding.radiogroupTstoreReviews.setOnCheckedChangeListener { group, checkedId ->
                    when (checkedId) {
                        R.id.radio_tstore_reviews_all -> {
                            vm.setQueryType(Pair(checkedId, null))
                        }
                        R.id.radio_tstore_reviews_title -> {
                            vm.setQueryType(Pair(checkedId, "가맹점"))
                        }
                        R.id.radio_tstore_reviews_content -> {
                            vm.setQueryType(Pair(checkedId, "내용"))
                        }
                        R.id.radio_tstore_reviews_creator -> {
                            vm.setQueryType(Pair(checkedId, "작성자"))
                        }
                    }
                    vm.storeReviews()
                }
            }
        }
        vm.viewEvent.observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                when(it.first) {
                    StoreReviewsViewModel.EVENT_REFRESH_MENU_REVIEWS -> {
                        adapter.submitData(viewLifecycleOwner.lifecycle, it.second as PagingData<StoreReviews.StoreReview>)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        disposable.clear()
        super.onDestroyView()
        _binding = null
    }
}