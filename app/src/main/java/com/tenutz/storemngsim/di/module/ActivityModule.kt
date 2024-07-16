package com.tenutz.storemngsim.di.module

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.application.MainActivity
import com.tenutz.storemngsim.core.result.ActivityResultFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@InstallIn(ActivityComponent::class)
@Module
class ActivityModule {

    @ActivityScoped
    @Provides
    fun provideActivityResultFactory(activity: Activity): ActivityResultFactory<Intent, ActivityResult> {
        return ActivityResultFactory.registerActivityForResult(activity as AppCompatActivity)
    }

    @ActivityScoped
    @Provides
    fun provideMainActivity(activity: Activity): MainActivity {
        return activity as MainActivity
    }

    @ActivityScoped
    @Provides
    fun provideDivider8dp(mainActivity: MainActivity): DividerItemDecoration? {
        return ContextCompat.getDrawable(mainActivity, R.drawable.divider_8dp)?.let { DividerItemDecoration(mainActivity, LinearLayoutManager.VERTICAL).apply { setDrawable(it) } }
    }
}