package com.tenutz.storemngsim.utils.ext

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.application.MainActivity

fun Fragment.mainActivity() = (requireActivity() as MainActivity)

fun MainActivity.currentFragment(): Fragment? = supportFragmentManager.findFragmentById(R.id.container)

fun MainActivity.currentDestinationId(): Int? = currentFragment()?.let { NavHostFragment.findNavController(it).currentDestination?.id }