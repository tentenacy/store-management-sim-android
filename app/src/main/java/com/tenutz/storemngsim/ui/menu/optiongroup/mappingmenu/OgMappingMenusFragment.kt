package com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.databinding.FragmentOgMappingMenusBinding
import com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.OgMappingMenusPagerAdapter.Companion.OG_MAIN_MENUS_PAGE_INDEX
import com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.OgMappingMenusPagerAdapter.Companion.OG_OPTION_MENUS_PAGE_INDEX
import com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.base.NavOgMappingMenuFragment
import com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.mainmenu.bs.OgMainMenuAddBeforeBottomSheetDialogV2
import com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.optionmenu.OgOptionMenuAddViewModel
import com.tenutz.storemngsim.utils.ext.mainActivity
import com.tenutz.storemngsim.utils.ext.navigateToMainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OgMappingMenusFragment: NavOgMappingMenuFragment() {

    private var _binding: FragmentOgMappingMenusBinding? = null
    val binding: FragmentOgMappingMenusBinding get() = _binding!!

    val args: OgMappingMenusFragmentArgs by navArgs()

    val vm: OgMappingMenusViewModel by navGraphViewModels(R.id.navigation_og_mapping_menu) {
        defaultViewModelProviderFactory
    }

    private val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int ) {
            val findFragmentByTag = childFragmentManager.findFragmentByTag(
                "f${
                    binding.vpagerOgMappingMenus.adapter?.getItemId(position)
                }"
            )
            when (position) {
                OG_OPTION_MENUS_PAGE_INDEX -> {
                    Logger.d("tab changed to OgOptionMenusTabFragment")
                    binding.fabOgMappingMenusAdd.setOnClickListener {
                        findNavController().navigate(OgMappingMenusFragmentDirections.actionOgMappingMenusFragmentToOgOptionMenuAddFragment(args.optionGroup))
                    }
                }
                OG_MAIN_MENUS_PAGE_INDEX -> {
                    Logger.d("tab changed to OgMainMenusTabFragment")
                    binding.fabOgMappingMenusAdd.setOnClickListener {
                        OgMainMenuAddBeforeBottomSheetDialogV2(
                            onClickListener = { id, item ->
                                when (id) {
                                    R.id.btn_bsog_main_menu_add_before_v2_search -> {
                                        findNavController().navigate(
                                            OgMappingMenusFragmentDirections.actionOgMappingMenusFragmentToOgMainMenuAddFragment(item ?: "0000", args.optionGroup)
                                        )
                                    }
                                }
                            }
                        ).show(childFragmentManager, "ogMainMenuAddBeforeBottomSheetDialogV2")
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOgMappingMenusBinding.inflate(inflater, container, false)

        binding.args = args.optionGroup
        binding.vm = vm

        binding.vpagerOgMappingMenus.adapter = OgMappingMenusPagerAdapter(this)

        TabLayoutMediator(binding.tabOgMappingMenus, binding.vpagerOgMappingMenus) { tab, position ->
            tab.text = when(position) {
                OG_OPTION_MENUS_PAGE_INDEX -> {
                    "옵션메뉴"
                }
                OG_MAIN_MENUS_PAGE_INDEX -> {
                    "메인메뉴"
                }
                else -> null
            }
        }.attach()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vpagerOgMappingMenus.registerOnPageChangeCallback(onPageChangeCallback)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.imageOgMappingMenusBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.imageOgMappingMenusHome.setOnClickListener {
            mainActivity().navigateToMainFragment()
        }
        binding.imageOgMappingMenusHamburger.setOnClickListener {
            mainActivity().binding.drawerMain.openDrawer(GravityCompat.END)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}