package com.tenutz.storemngsim.application

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.chibatching.kotpref.Kotpref
import com.facebook.appevents.AppEventsLogger
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import io.reactivex.rxjava3.exceptions.UndeliverableException
import io.reactivex.rxjava3.plugins.RxJavaPlugins

@HiltAndroidApp
class GlobalApplication : Application() {

    override fun onCreate() {

        super.onCreate()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        Logger.addLogAdapter(AndroidLogAdapter())
        Kotpref.init(this)
        KakaoSdk.init(this, BuildConfig.SOCIAL_KAKAO_CLIENT_ID)
        NaverIdLoginSDK.initialize(
            applicationContext,
            BuildConfig.SOCIAL_NAVER_CLIENT_ID,
            BuildConfig.SOCIAL_NAVER_CLIENT_SECRET,
            BuildConfig.SOCIAL_NAVER_CLIENT_NAME
        )
        AppEventsLogger.activateApp(this)

        RxJavaPlugins.setErrorHandler { e ->
            if (e is UndeliverableException) {
                // Merely log undeliverable exceptions
                Logger.e("${e.message}")
            } else {
                // Forward all others to current thread's uncaught exception handler
                Thread.currentThread().also { thread ->
                    thread.uncaughtExceptionHandler.uncaughtException(thread, e)
                }
            }
        }
    }
}