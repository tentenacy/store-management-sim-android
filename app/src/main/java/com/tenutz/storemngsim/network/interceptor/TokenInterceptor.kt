package com.tenutz.storemngsim.network.interceptor

import com.orhanobut.logger.Logger
import com.tenutz.storemngsim.data.datasource.api.err.ErrorCode
import com.tenutz.storemngsim.network.subject.Subject
import com.tenutz.storemngsim.data.datasource.sharedpref.Token
import com.tenutz.storemngsim.network.observer.TokenExpirationObserver
import com.tenutz.storemngsim.utils.toErrorResponseOrNull
import com.tenutz.storemngsim.utils.toTokenResponseOrNull
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor: Interceptor, Subject<TokenExpirationObserver>() {

    private var accessTokenErrorOccurred = false
    private var refreshTokenErrorOccurred = false

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", if(Token.accessToken.isNotBlank()) "Bearer ${Token.accessToken}" else "")
            .build()
        val response = chain.proceed(request)

        if (!response.isSuccessful) {
            Logger.e("response is not successful")
            response.peekBody(2048).toErrorResponseOrNull()?.apply {
                Logger.e("errorResponse: $this")
                when (code) {
                    ErrorCode.ACCESS_TOKEN_ERROR.code -> {
                        if(!accessTokenErrorOccurred) {
                            notifyTokenExpired()
                            accessTokenErrorOccurred = true
                        }
                    }
                    ErrorCode.REFRESH_TOKEN_ERROR.code, ErrorCode.REFRESH_TOKEN_NOT_FOUND.code -> {
                        if(!refreshTokenErrorOccurred) {
                            notifyRefreshTokenExpired()
                            refreshTokenErrorOccurred = true
                        }
                    }
                }
            }
        } else {
            response.peekBody(2048).toTokenResponseOrNull()?.apply {
                grantType?.let { Token.grantType = it }
                accessToken?.let { Token.accessToken = it }
                refreshToken?.let { Token.refreshToken = it }
                accessTokenExpiresIn?.let { Token.accessTokenExpireIn = it }

                accessTokenErrorOccurred = false
                refreshTokenErrorOccurred = false
            }
        }

        return response
    }

    private fun notifyTokenExpired() {
        observers.forEach(TokenExpirationObserver::onTokenExpired)
    }

    private fun notifyRefreshTokenExpired() {
        observers.forEach(TokenExpirationObserver::onRefreshTokenExpired)
    }
}