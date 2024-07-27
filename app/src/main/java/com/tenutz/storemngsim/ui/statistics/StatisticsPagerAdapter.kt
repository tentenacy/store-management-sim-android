package com.tenutz.storemngsim.ui.statistics

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tenutz.storemngsim.ui.statistics.card.StatisticsCardTabFragment
import com.tenutz.storemngsim.ui.statistics.menu.StatisticsMenuTabFragment
import com.tenutz.storemngsim.ui.statistics.time.StatisticsTimeTabFragment

class StatisticsPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    companion object {

        const val STATISTICS_MENU_PAGE_INDEX = 0
        const val STATISTICS_TIME_PAGE_INDEX = 1
        const val STATISTICS_CARD_PAGE_INDEX = 2
    }

    private val tabFragmentCreators: Map<Int, () -> Fragment> = mapOf(
        STATISTICS_MENU_PAGE_INDEX to { StatisticsMenuTabFragment() },
        STATISTICS_TIME_PAGE_INDEX to { StatisticsTimeTabFragment() },
        STATISTICS_CARD_PAGE_INDEX to { StatisticsCardTabFragment() },
    )

    override fun getItemCount(): Int = tabFragmentCreators.size
    override fun createFragment(position: Int): Fragment = tabFragmentCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
}