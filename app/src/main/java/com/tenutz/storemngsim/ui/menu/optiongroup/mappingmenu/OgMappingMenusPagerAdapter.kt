package com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.mainmenu.OgMainMenusTabFragment
import com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.optionmenu.OgOptionMenusTabFragment
import java.lang.IndexOutOfBoundsException

class OgMappingMenusPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    companion object {

        const val OG_OPTION_MENUS_PAGE_INDEX = 0
        const val OG_MAIN_MENUS_PAGE_INDEX = 1
    }

    private val tabFragmentCreators: Map<Int, () -> Fragment> = mapOf(
        OG_OPTION_MENUS_PAGE_INDEX to { OgOptionMenusTabFragment() },
        OG_MAIN_MENUS_PAGE_INDEX to { OgMainMenusTabFragment() },
    )

    override fun getItemCount(): Int = tabFragmentCreators.size
    override fun createFragment(position: Int): Fragment = tabFragmentCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
}