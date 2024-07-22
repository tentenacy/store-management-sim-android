package com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu

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
import com.tenutz.storemngsim.databinding.FragmentOgMappingMenusBinding
import com.tenutz.storemngsim.ui.base.BaseFragment
import com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.OgMappingMenusPagerAdapter.Companion.OG_MAIN_MENUS_PAGE_INDEX
import com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.OgMappingMenusPagerAdapter.Companion.OG_OPTION_MENUS_PAGE_INDEX
import com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.args.MappingMenusNavArgs
import com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.mainmenu.bs.OgMainMenuAddBeforeBottomSheetDialog
import com.tenutz.storemngsim.utils.ext.mainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OgMappingMenusFragment: BaseFragment() {

    private var _binding: FragmentOgMappingMenusBinding? = null
    val binding: FragmentOgMappingMenusBinding get() = _binding!!

    lateinit var args: MappingMenusNavArgs

    val vm: OgMappingMenusViewModel by navGraphViewModels(R.id.navigation_og_mapping_menu) {
        defaultViewModelProviderFactory
    }

    private val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            val findFragmentByTag = childFragmentManager.findFragmentByTag(
                "f${
                    binding.vpagerOgMappingMenus.adapter?.getItemId(position)
                }"
            )
            when (position) {
                OG_OPTION_MENUS_PAGE_INDEX -> {
                    Logger.d("tab changed to OgOptionMenusTabFragment")
                    binding.fabOgMappingMenusAdd.setOnClickListener {
                        findNavController().navigate(OgMappingMenusFragmentDirections.actionOgMappingMenusFragmentToOgOptionMenuAddFragment(args.optionGroupCode))
                    }
                }
                OG_MAIN_MENUS_PAGE_INDEX -> {
                    Logger.d("tab changed to OgMainMenusTabFragment")
                    binding.fabOgMappingMenusAdd.setOnClickListener {
                        OgMainMenuAddBeforeBottomSheetDialog(
                            onClickListener = { id, item ->
                                when (id) {
                                    R.id.btn_bsog_main_menu_add_before_search -> {
                                        (item as Triple<String?, String?, String?>).takeIf { it.first?.isNotBlank() == true }?.let {
                                            findNavController().navigate(
                                                OgMappingMenusFragmentDirections.actionOgMappingMenusFragmentToOgMainMenuAddFragment(
                                                    args.optionGroupCode,
                                                    it.first!!,
                                                    it.second,
                                                    it.third,
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        ).show(childFragmentManager, "ogMainMenuAddBeforeBottomSheetDialog")
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        args = requireArguments().getParcelable("optionGroup")!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOgMappingMenusBinding.inflate(inflater, container, false)

        binding.args = args
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
            findNavController().navigate(R.id.action_global_mainFragment)
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