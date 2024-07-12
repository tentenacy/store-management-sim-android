package com.tenutz.storemngsim.application.manager

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.orhanobut.logger.Logger
import javax.inject.Inject

class GoogleLoginManager @Inject constructor(
    private val googleSignInClient: GoogleSignInClient
) : OAuthLoginManagerSubject() {
    override fun logout() {
        googleSignInClient.signOut()
        Logger.d("logout")
    }

    override fun withdrawal() {
    }
}