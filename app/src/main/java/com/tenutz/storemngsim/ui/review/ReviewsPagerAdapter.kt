package com.tenutz.storemngsim.ui.review

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tenutz.storemngsim.ui.review.menu.MenuReviewsTabFragment
import com.tenutz.storemngsim.ui.review.store.StoreReviewsTabFragment

class ReviewsPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    companion object {

        const val MENU_REVIEWS_PAGE_INDEX = 0
        const val STORE_REVIEWS_PAGE_INDEX = 1
    }

    private val tabFragmentCreators: Map<Int, () -> Fragment> = mapOf(
        MENU_REVIEWS_PAGE_INDEX to { MenuReviewsTabFragment() },
        STORE_REVIEWS_PAGE_INDEX to { StoreReviewsTabFragment() },
    )

    override fun getItemCount(): Int = tabFragmentCreators.size
    override fun createFragment(position: Int): Fragment = tabFragmentCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
}