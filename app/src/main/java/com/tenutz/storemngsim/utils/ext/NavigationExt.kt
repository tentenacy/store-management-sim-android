package com.tenutz.storemngsim.utils.ext

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.tenutz.storemngsim.NavigationMainDirections
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.application.MainActivity
import com.tenutz.storemngsim.ui.menu.MainMenusBeforeBottomSheetDialogV2
import com.tenutz.storemngsim.ui.menu.mainmenu.args.MainMenusNavArgs

fun <T> Fragment.getNavigationResult(key: String = "result") =
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)

fun <T> Fragment.setNavigationResult(result: T, key: String = "result") {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
}

val MainActivity.navController get() = run { binding.container.getFragment<NavHostFragment>().navController }

fun Fragment.isOnBackStack(@IdRes id: Int): Boolean = try { findNavController().getBackStackEntry(id); true } catch(e: Throwable) { false }

fun MainActivity.navigateToLoginFragment() {
    if (currentDestinationId() == R.id.loginFragment) {
        binding.drawerMain.closeDrawer(GravityCompat.END)
    } else {
//        currentFragment()?.findNavController()?.popBackStack(R.id.loginFragment, true)
//        currentFragment()?.findNavController()?.navigate(R.id.action_global_to_loginFragment)

        currentFragment()?.findNavController()?.run {
            navigate(
                R.id.navigation_main,
                null,
                NavOptions.Builder().setPopUpTo(R.id.mainFragment, true).build()
            )
        }
    }
}

fun MainActivity.navigateToMainFragment() {
    if(currentDestinationId() == R.id.mainFragment) {
        binding.drawerMain.closeDrawer(GravityCompat.END)
    } else {
//        currentFragment()?.findNavController()?.popBackStack(R.id.loginFragment, true)
//        currentFragment()?.findNavController()?.navigate(R.id.action_global_to_loginFragment)

        currentFragment()?.findNavController()?.run {
            navigate(
                R.id.action_global_mainFragment,
                null,
                NavOptions.Builder()
                    .setPopUpTo(R.id.mainFragment, true)
                    .setEnterAnim(0)
                    .setExitAnim(0)
                    .setPopEnterAnim(0)
                    .setPopExitAnim(0)
                    .build()
            )
        }
    }
}

fun MainActivity.navigateToSubCategoriesFragment() {
    if(currentDestinationId() == R.id.subCategoriesFragment) {
        binding.drawerMain.closeDrawer(GravityCompat.END)
    } else {
        currentFragment()?.findNavController()?.navigate(NavigationMainDirections.showSubCategory())
    }
}

fun MainActivity.navigateToMainMenusFragmentV2() {
    if(currentDestinationId() == R.id.mainMenusFragment) {
        binding.drawerMain.closeDrawer(GravityCompat.END)
    } else {
        MainMenusBeforeBottomSheetDialogV2(
            onClickListener = { id, item ->
                when (id) {
                    R.id.btn_bsmain_menus_before_v2_search -> {
                        currentFragment()?.findNavController()?.navigate(NavigationMainDirections.showMainMenu(MainMenusNavArgs(item.first, item.second)))
                    }
                }
            }
        ).show(supportFragmentManager, "mainMenusBeforeBottomSheetDialogV2")
    }
}

fun MainActivity.navigateToOptionMenusFragment() {
    if(currentDestinationId() == R.id.optionMenusFragment) {
        binding.drawerMain.closeDrawer(GravityCompat.END)
    } else {
        currentFragment()?.findNavController()?.navigate(NavigationMainDirections.showOptionMenu())
    }
}

fun MainActivity.navigateToOptionGroupsFragment() {
    if(currentDestinationId() == R.id.optionGroupsFragment) {
        binding.drawerMain.closeDrawer(GravityCompat.END)
    } else {
        currentFragment()?.findNavController()?.navigate(NavigationMainDirections.showOptionGroup())
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