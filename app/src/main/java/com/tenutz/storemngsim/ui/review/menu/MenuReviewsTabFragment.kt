package com.tenutz.storemngsim.ui.review.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.data.datasource.paging.entity.MenuReviews
import com.tenutz.storemngsim.databinding.TabMenuReviewsBinding
import com.tenutz.storemngsim.ui.review.ReviewsFragmentDirections
import com.tenutz.storemngsim.ui.review.ReviewsViewModel
import com.tenutz.storemngsim.ui.review.bs.ReviewsBottomSheetDialog
import com.tenutz.storemngsim.ui.review.menu.args.MenuReplyPostNavArgs
import com.tenutz.storemngsim.utils.ext.editTextObservable
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class MenuReviewsTabFragment: Fragment() {

    private val disposable = CompositeDisposable()

    private var _binding: TabMenuReviewsBinding? = null
    val binding: TabMenuReviewsBinding get() = _binding!!

    val vm: MenuReviewsViewModel by navGraphViewModels(R.id.navigation_review) {
        defaultViewModelProviderFactory
    }

    private val pVm: ReviewsViewModel by navGraphViewModels(R.id.navigation_review) {
        defaultViewModelProviderFactory
    }

    private val adapter: MenuReviewsAdapter by lazy {
        MenuReviewsAdapter { id, item ->
            when(id) {
                R.id.text_itmenu_reviews_reply_post -> {
                    val menuReview = item as MenuReviews.MenuReview
                    findNavController().navigate(ReviewsFragmentDirections.actionReviewsFragmentToMenuReplyPostFragment(
                        MenuReplyPostNavArgs(
                            seq = menuReview.seq,
                            siteCode = menuReview.siteCode,
                            storeCode = menuReview.storeCode,
                            mainCategoryCode = menuReview.mainCategoryCode,
                            middleCategoryCode = menuReview.middleCategoryCode,
                            subCategoryCode = menuReview.subCategoryCode,
                            menuCode = menuReview.menuCode,
                            menuName = menuReview.menuName,
                            createdBy = menuReview.createdBy,
                            createdAt = menuReview.createdAt,
                            content = menuReview.content,
                            keyword = menuReview.keyword,
                            level = menuReview.level,
                            rating = menuReview.rating,
                            sno = menuReview.sno,
                        )
                    ))
                }
                R.id.text_itmenu_reviews_reply_edit -> {
                    val menuReview = item as MenuReviews.MenuReview
                    findNavController().navigate(ReviewsFragmentDirections.actionReviewsFragmentToMenuReplyPostFragment(
                        MenuReplyPostNavArgs(
                            seq = menuReview.seq,
                            siteCode = menuReview.siteCode,
                            storeCode = menuReview.storeCode,
                            mainCategoryCode = menuReview.mainCategoryCode,
                            middleCategoryCode = menuReview.middleCategoryCode,
                            subCategoryCode = menuReview.subCategoryCode,
                            menuCode = menuReview.menuCode,
                            menuName = menuReview.menuName,
                            createdBy = menuReview.createdBy,
                            createdAt = menuReview.createdAt,
                            content = menuReview.content,
                            keyword = menuReview.keyword,
                            level = menuReview.level,
                            rating = menuReview.rating,
                            sno = menuReview.sno,
                            menuReviewReply = menuReview.menuReviewReply,
                        )
                    ))
                }
                R.id.text_itmenu_reviews_reply_delete -> {
                    val replySeq = item as Long
                    ReviewsBottomSheetDialog { id2, _ ->
                        when(id2) {
                            R.id.btn_bsreviews_delete -> {
                                vm.deleteMenuReviewReply(replySeq) {
                                    vm.menuReviews()
                                }
                            }
                        }
                    }.show(childFragmentManager, "reviewsBottomSheetDialog")
                }
            }
        }
    }

    var dividerItemDecoration: DividerItemDecoration? = null
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.menuReviews()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = TabMenuReviewsBinding.inflate(inflater, container, false)

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
        binding.recyclerTmenuReviews.adapter = adapter
        dividerItemDecoration?.let { binding.recyclerTmenuReviews.addItemDecoration(it) }
        editTextObservable(binding.editTmenuReviewsSearch)
            .debounce(500, TimeUnit.MILLISECONDS).skip(1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                vm.menuReviews(it)
            }
            .addTo(disposable)
    }

    private fun observeData() {
        vm.queryType.observe(viewLifecycleOwner) {
            it.first?.let {
                binding.radiogroupTmenuReviews.setOnCheckedChangeListener(null)
                binding.radiogroupTmenuReviews.check(it)
                binding.radiogroupTmenuReviews.setOnCheckedChangeListener { group, checkedId ->
                    when (checkedId) {
                        R.id.radio_tmenu_reviews_all -> {
                            vm.setQueryType(Pair(checkedId, null))
                        }
                        R.id.radio_tmenu_reviews_title -> {
                            vm.setQueryType(Pair(checkedId, "메뉴"))
                        }
                        R.id.radio_tmenu_reviews_content -> {
                            vm.setQueryType(Pair(checkedId, "내용"))
                        }
                        R.id.radio_tmenu_reviews_creator -> {
                            vm.setQueryType(Pair(checkedId, "작성자"))
                        }
                    }
                    vm.menuReviews()
                }
            }
        }
        vm.viewEvent.observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                when(it.first) {
                    MenuReviewsViewModel.EVENT_REFRESH_MENU_REVIEWS -> {
                        adapter.submitData(viewLifecycleOwner.lifecycle, it.second as PagingData<MenuReviews.MenuReview>)
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