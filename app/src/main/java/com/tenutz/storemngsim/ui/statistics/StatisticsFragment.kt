package com.tenutz.storemngsim.ui.statistics

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
import com.tenutz.storemngsim.databinding.FragmentStatisticsBinding
import com.tenutz.storemngsim.ui.base.BaseFragment
import com.tenutz.storemngsim.ui.common.DatePickerDialog
import com.tenutz.storemngsim.utils.ext.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticsFragment: BaseFragment() {

    private var _binding: FragmentStatisticsBinding? = null
    val binding: FragmentStatisticsBinding get() = _binding!!

    val vm: StatisticsViewModel by navGraphViewModels(R.id.navigation_statistics) {
        defaultViewModelProviderFactory
    }

    private val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {

            binding.constraintStatisticsMenuContainer.visibility = if(position == StatisticsPagerAdapter.STATISTICS_MENU_PAGE_INDEX) View.VISIBLE else View.GONE
            binding.constraintStatisticsTimeContainer.visibility = if(position == StatisticsPagerAdapter.STATISTICS_TIME_PAGE_INDEX) View.VISIBLE else View.GONE
            binding.constraintStatisticsCardContainer.visibility = if(position == StatisticsPagerAdapter.STATISTICS_CARD_PAGE_INDEX) View.VISIBLE else View.GONE

            when (position) {
                StatisticsPagerAdapter.STATISTICS_MENU_PAGE_INDEX -> {
                    Logger.d("tab changed to StatisticsMenuTabFragment")
                }
                StatisticsPagerAdapter.STATISTICS_TIME_PAGE_INDEX -> {
                    Logger.d("tab changed to StatisticsTimeTabFragment")
                }
                StatisticsPagerAdapter.STATISTICS_CARD_PAGE_INDEX -> {
                    Logger.d("tab changed to StatisticsCardTabFragment")
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)

        binding.vm = vm
        binding.lifecycleOwner = this
        binding.vpagerStatistics.adapter = StatisticsPagerAdapter(this)

        TabLayoutMediator(binding.tabStatistics, binding.vpagerStatistics) { tab, position ->
            tab.text = when(position) {
                StatisticsPagerAdapter.STATISTICS_MENU_PAGE_INDEX -> {
                    "메뉴"
                }
                StatisticsPagerAdapter.STATISTICS_TIME_PAGE_INDEX -> {
                    "시간대"
                }
                StatisticsPagerAdapter.STATISTICS_CARD_PAGE_INDEX -> {
                    "카드사"
                }
                else -> null
            }
        }.attach()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vpagerStatistics.registerOnPageChangeCallback(onPageChangeCallback)
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.imageStatisticsBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.imageStatisticsHome.setOnClickListener {
            findNavController().navigate(R.id.action_global_mainFragment)
        }
        binding.imageStatisticsHamburger.setOnClickListener {
            mainActivity().binding.drawerMain.openDrawer(GravityCompat.END)
        }
        binding.linearStatisticsMenuMainSdateContainer.setOnClickListener {
            DatePickerDialog(localDateFrom(binding.textStatisticsMenuMainSdate.text.toString())) {
                vm.setMenuDate(dateFrom = it?.toDate()?.start())
                vm.setMenuButton(null)
            }.show(childFragmentManager, "datePickerDialog")
        }
        binding.linearStatisticsMenuMainEdateContainer.setOnClickListener {
            DatePickerDialog(localDateFrom(binding.textStatisticsMenuMainEdate.text.toString())) {
                vm.setMenuDate(dateTo = it?.toDate()?.end())
                vm.setMenuButton(null)
            }.show(childFragmentManager, "datePickerDialog")
        }
        binding.constraintStatisticsTimeMainDateContainer.setOnClickListener {
            DatePickerDialog(localDateFrom(binding.textStatisticsTimeMainDate.text.toString())) {
                when (vm.timeButton.value) {
                    R.id.radio_statistics_time_main_morning -> {
                        vm.setTimeDate(it?.toDate()?.start(), it?.toDate()?.noon())
                    }
                    R.id.radio_statistics_time_main_afternoon -> {
                        vm.setTimeDate(it?.toDate()?.noon(), it?.toDate()?.end())
                    }
                    R.id.radio_statistics_time_main_day -> {
                        vm.setTimeDate(it?.toDate()?.start(), it?.toDate()?.end())
                    }
                }
                vm.setTimeButton(null)
            }.show(childFragmentManager, "datePickerDialog")
        }
        binding.imageStatisticsTimeMainPrev.setOnClickListener {
            val yesterday = dateFrom(binding.textStatisticsTimeMainDate.text.toString())?.yesterday()
            when (vm.timeButton.value) {
                R.id.radio_statistics_time_main_morning -> {
                    vm.setTimeDate(yesterday, yesterday?.noon())
                }
                R.id.radio_statistics_time_main_afternoon -> {
                    vm.setTimeDate(yesterday?.noon(), yesterday?.end())
                }
                R.id.radio_statistics_time_main_day -> {
                    vm.setTimeDate(yesterday, yesterday?.end())
                }
            }
            vm.setTimeButton(null)
        }
        binding.imageStatisticsTimeMainNext.setOnClickListener {
            val tomorrow = dateFrom(binding.textStatisticsTimeMainDate.text.toString())?.tomorrow()
            when (vm.timeButton.value) {
                R.id.radio_statistics_time_main_morning -> {
                    vm.setTimeDate(tomorrow, tomorrow?.noon())
                }
                R.id.radio_statistics_time_main_afternoon -> {
                    vm.setTimeDate(tomorrow?.noon(), tomorrow?.end())
                }
                R.id.radio_statistics_time_main_day -> {
                    vm.setTimeDate(tomorrow, tomorrow?.end())
                }
            }
            vm.setTimeButton(null)
        }
        binding.linearStatisticsCardMainSdateContainer.setOnClickListener {
            DatePickerDialog(localDateFrom(binding.textStatisticsCardMainSdate.text.toString())) {
                vm.setCardDate(dateFrom = it?.toDate()?.start())
                vm.setCardButton(null)
            }.show(childFragmentManager, "datePickerDialog")
        }
        binding.linearStatisticsCardMainEdateContainer.setOnClickListener {
            DatePickerDialog(localDateFrom(binding.textStatisticsCardMainEdate.text.toString())) {
                vm.setCardDate(dateTo = it?.toDate()?.end())
                vm.setCardButton(null)
            }.show(childFragmentManager, "datePickerDialog")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}