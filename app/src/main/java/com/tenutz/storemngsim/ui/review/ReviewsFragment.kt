package com.tenutz.storemngsim.ui.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.databinding.FragmentMainBinding
import com.tenutz.storemngsim.databinding.FragmentReviewsBinding
import com.tenutz.storemngsim.databinding.FragmentSignupFormBinding
import com.tenutz.storemngsim.ui.base.BaseFragment
import com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.OgMappingMenusPagerAdapter
import com.tenutz.storemngsim.utils.ext.mainActivity
import com.tenutz.storemngsim.utils.ext.navigateToMainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewsFragment: BaseFragment() {

    private var _binding: FragmentReviewsBinding? = null
    val binding: FragmentReviewsBinding get() = _binding!!

    val vm: ReviewsViewModel by navGraphViewModels(R.id.navigation_review) {
        defaultViewModelProviderFactory
    }

    private val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            when (position) {
                ReviewsPagerAdapter.MENU_REVIEWS_PAGE_INDEX -> {
                    Logger.d("tab changed to MenuReviewsTabFragment")
                }
                ReviewsPagerAdapter.STORE_REVIEWS_PAGE_INDEX -> {
                    Logger.d("tab changed to StoreReviewsTabFragment")
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentReviewsBinding.inflate(inflater, container, false)

        binding.vpagerReviews.adapter = ReviewsPagerAdapter(this)

        TabLayoutMediator(binding.tabReviews, binding.vpagerReviews) { tab, position ->
            tab.text = when(position) {
                ReviewsPagerAdapter.MENU_REVIEWS_PAGE_INDEX -> {
                    "메뉴 평가"
                }
                ReviewsPagerAdapter.STORE_REVIEWS_PAGE_INDEX -> {
                    "가맹점 평가"
                }
                else -> null
            }
        }.attach()
        
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vpagerReviews.registerOnPageChangeCallback(onPageChangeCallback)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.imageReviewsBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.imageReviewsHome.setOnClickListener {
            mainActivity().navigateToMainFragment()
        }
        binding.imageReviewsHamburger.setOnClickListener {
            mainActivity().binding.drawerMain.openDrawer(GravityCompat.END)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}