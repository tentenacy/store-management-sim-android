package com.tenutz.storemngsim.ui.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.tenutz.storemngsim.utils.ext.mainActivity

open class BaseFragment: Fragment() {

    private lateinit var onBackPressedCallback: OnBackPressedCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (mainActivity().binding.drawerMain.isDrawerOpen(GravityCompat.END)) {
                    mainActivity().binding.drawerMain.closeDrawer(GravityCompat.END)
                } else {
                    remove()
                    mainActivity().onBackPressed()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity().binding.drawerMain.closeDrawer(GravityCompat.END)
    }

}