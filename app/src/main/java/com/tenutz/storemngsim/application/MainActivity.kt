package com.tenutz.storemngsim.application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tenutz.storemngsim.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_StoreManagementSim)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}