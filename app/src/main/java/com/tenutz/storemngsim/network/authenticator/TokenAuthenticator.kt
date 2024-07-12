package com.tenutz.storemngsim.network.authenticator

import com.tenutz.storemngsim.data.datasource.api.UserApi
import com.tenutz.storemngsim.data.datasource.api.dto.common.TokenRequest
import com.tenutz.storemngsim.data.datasource.api.err.ErrorCode
import com.tenutz.storemngsim.data.datasource.sharedpref.Token
import com.tenutz.storemngsim.network.observer.TokenExpirationObserver
import com.tenutz.storemngsim.network.subject.Subject
import com.tenutz.storemngsim.utils.ext.toDeferred
import com.tenutz.storemngsim.utils.toErrorResponseOrNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.HttpException

class TokenAuthenticator constructor(
    private val userApi: UserApi,
) : Subject<TokenExpirationObserver>(), Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {

        response.peekBody(2048).toErrorResponseOrNull()?.apply {
            when (code) {
                ErrorCode.ACCESS_TOKEN_ERROR.code -> {
                    try {

                        val token = runBlocking {
                            withContext(Dispatchers.Default) {
                                userApi.reissue(TokenRequest(Token.accessToken, Token.refreshToken))
                                    .toDeferred(this)
                                    .await()
                            }
                        }

                        Token.save(
                            token.grantType,
                            token.accessToken,
                            token.refreshToken,
                            token.accessTokenExpiresIn,
                        )

                        return response.request
                            .newBuilder()
                            .removeHeader("Authorization")
                            .addHeader("Authorization", "Bearer ${Token.accessToken}")
                            .build()
                    } catch (e: HttpException) {
                        notifyRefreshTokenExpired()
                    }
                }
                ErrorCode.REFRESH_TOKEN_ERROR.code, ErrorCode.REFRESH_TOKEN_NOT_FOUND.code -> {
                    notifyRefreshTokenExpired()
                }
            }
        }

        return null
    }

    private fun notifyRefreshTokenExpired() {
        observers.forEach(TokenExpirationObserver::onRefreshTokenExpired)
    }
}
