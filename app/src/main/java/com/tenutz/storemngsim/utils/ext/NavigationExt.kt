package com.tenutz.storemngsim.utils.ext

import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.application.MainActivity
import com.tenutz.storemngsim.ui.menu.MainMenusBeforeBottomSheetDialog

fun <T> Fragment.getNavigationResult(key: String = "result") =
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)

fun <T> Fragment.setNavigationResult(result: T, key: String = "result") {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
}

fun MainActivity.navigateToLoginFragment() {
    if(currentDestinationId() == R.id.loginFragment) {
        binding.drawerMain.closeDrawer(GravityCompat.END)
    } else {
//        currentFragment()?.findNavController()?.popBackStack(R.id.loginFragment, true)
//        currentFragment()?.findNavController()?.navigate(R.id.action_global_to_loginFragment)
        currentFragment()?.findNavController()?.run {
            navigate(R.id.navigation_main_xml, null, NavOptions.Builder().setPopUpTo(R.id.mainFragment, true).build())
        }
    }
}

fun MainActivity.navigateToMainCategoriesFragment() {
    if(currentDestinationId() == R.id.mainCategoriesFragment) {
        binding.drawerMain.closeDrawer(GravityCompat.END)
    } else {
        currentFragment()?.findNavController()?.navigate(R.id.action_global_nav_mainCategory)
    }
}

fun MainActivity.navigateToMainMenusFragment() {
    if(currentDestinationId() == R.id.mainMenusFragment) {
        binding.drawerMain.closeDrawer(GravityCompat.END)
    } else {
        MainMenusBeforeBottomSheetDialog(
            onClickListener = { id, item ->
                when (id) {
                    R.id.btn_bsmain_menus_before_search -> {
                        (item as Triple<String?, String?, String?>).takeIf { it.first?.isNotBlank() == true && it.second?.isNotBlank() == true && it.third?.isNotBlank() == true }
                            ?.let {
                                currentFragment()?.findNavController()?.navigate(
                                    R.id.action_global_nav_mainMenu,
                                    Bundle().apply {
                                        putString("mainCategoryCode", it.first)
                                        putString("middleCategoryCode", it.second)
                                        putString("subCategoryCode", it.third)
                                    }
                                )
                            }
                    }
                }
            }
        ).show(supportFragmentManager, "mainMenusBeforeBottomSheetDialog")
    }
}

fun MainActivity.navigateToOptionMenusFragment() {
    if(currentDestinationId() == R.id.optionMenusFragment) {
        binding.drawerMain.closeDrawer(GravityCompat.END)
    } else {
        currentFragment()?.findNavController()?.navigate(R.id.action_global_nav_optionMenu)
    }
}

fun MainActivity.navigateToOptionGroupsFragment() {
    if(currentDestinationId() == R.id.optionGroupsFragment) {
        binding.drawerMain.closeDrawer(GravityCompat.END)
    } else {
        currentFragment()?.findNavController()?.navigate(R.id.action_global_nav_optionGroup)
    }
}

fun MainActivity.navigateToStatisticsFragment() {
    if(currentDestinationId() == R.id.statisticsFragment) {
        binding.drawerMain.closeDrawer(GravityCompat.END)
    } else {
        currentFragment()?.findNavController()?.navigate(R.id.action_global_nav_statistics)
    }
}

fun MainActivity.navigateToSalesFragment() {
    if(currentDestinationId() == R.id.salesFragment) {
        binding.drawerMain.closeDrawer(GravityCompat.END)
    } else {
        currentFragment()?.findNavController()?.navigate(R.id.action_global_to_salesFragment)
    }
}

fun MainActivity.navigateToReviewsFragment() {
    if(currentDestinationId() == R.id.reviewsFragment) {
        binding.drawerMain.closeDrawer(GravityCompat.END)
    } else {
        currentFragment()?.findNavController()?.navigate(R.id.action_global_nav_review)
    }
}

fun MainActivity.navigateToHelpsFragment() {
    if(currentDestinationId() == R.id.helpsFragment) {
        binding.drawerMain.closeDrawer(GravityCompat.END)
    } else {
        currentFragment()?.findNavController()?.navigate(R.id.action_global_helpsFragment)
    }
}

fun MainActivity.navigateToNotificationFragment() {
    if(currentDestinationId() == R.id.notificationFragment) {
        binding.drawerMain.closeDrawer(GravityCompat.END)
    } else {
        currentFragment()?.findNavController()?.navigate(R.id.action_global_notificationFragment)
    }
}

fun MainActivity.navigateToSettingsFragment() {
    if(currentDestinationId() == R.id.settingsFragment) {
        binding.drawerMain.closeDrawer(GravityCompat.END)
    } else {
        currentFragment()?.findNavController()?.navigate(R.id.action_global_settingsFragment)
    }
}