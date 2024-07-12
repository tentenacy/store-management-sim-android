package com.tenutz.storemngsim.application

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.core.result.ActivityResultFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var activityResultFactory: ActivityResultFactory<Intent, ActivityResult>

    val vm: GlobalViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_StoreManagementSim)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vm
    }
}