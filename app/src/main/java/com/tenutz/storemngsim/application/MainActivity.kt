package com.tenutz.storemngsim.application

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.core.result.ActivityResultFactory
import dagger.hilt.android.AndroidEntryPoint
import java.security.MessageDigest
import java.util.*
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