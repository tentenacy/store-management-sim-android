package com.tenutz.storemngsim.di.module

import androidx.fragment.app.Fragment
import com.nhn.android.naverlogin.OAuthLogin
import com.tenutz.storemngsim.ui.login.handler.FacebookOAuthLoginHandler
import com.tenutz.storemngsim.ui.login.handler.GoogleOAuthLoginHandler
import com.tenutz.storemngsim.ui.login.handler.KakaoOAuthLoginHandler
import com.tenutz.storemngsim.ui.login.handler.NaverOAuthLoginHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@InstallIn(FragmentComponent::class)
@Module
class LoginHandlerModule {

    @Provides
    fun provideNaverOAuthLoginHandler(fragment: Fragment, naverLoginManager: OAuthLogin): NaverOAuthLoginHandler {
        return NaverOAuthLoginHandler(fragment, naverLoginManager)
    }

    @Provides
    fun provideFacebookOAuthLoginHandler(fragment: Fragment): FacebookOAuthLoginHandler {
        return FacebookOAuthLoginHandler(fragment)
    }

    @Provides
    fun provideKakaoOAuthLoginHandler(fragment: Fragment): KakaoOAuthLoginHandler {
        return KakaoOAuthLoginHandler(fragment)
    }

    @Provides
    fun provideGoogleOAuothLoginHandler(fragment: Fragment): GoogleOAuthLoginHandler {
        return GoogleOAuthLoginHandler(fragment)
    }
}