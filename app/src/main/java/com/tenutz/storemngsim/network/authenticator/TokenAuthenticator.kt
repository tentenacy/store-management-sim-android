package com.tenutz.storemngsim.network.authenticator

import com.tenutz.storemngsim.data.datasource.api.UserApi
import com.tenutz.storemngsim.data.datasource.api.dto.common.TokenRequest
import com.tenutz.storemngsim.data.datasource.api.dto.common.TokenResponse
import com.tenutz.storemngsim.data.datasource.api.err.ErrorCode
import com.tenutz.storemngsim.data.datasource.sharedpref.Token
import com.tenutz.storemngsim.network.observer.TokenExpirationObserver
import com.tenutz.storemngsim.network.subject.Subject
import com.tenutz.storemngsim.utils.ext.toDeferred
import com.tenutz.storemngsim.utils.ext.toErrorResponseOrNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.rx3.awaitSingleOrNull
import kotlinx.coroutines.withContext
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(
    private val userApi: UserApi,
) : Subject<TokenExpirationObserver>(), Authenticator {

    private fun notifyRefreshTokenExpired() {
        observers.forEach(TokenExpirationObserver::onRefreshTokenExpired)
    }

    override fun authenticate(route: Route?, response: Response): Request? {

        //Access Token을 갱신할 필요가 없는 경우 별도 처리
        response.peekBody(2048).toErrorResponseOrNull()?.apply {
            when(code) {
                ErrorCode.USER_NOT_FOUND.code -> {
                    //회원가입 하는 경우 무시
                    if(Token.refreshToken.isBlank()) return null

                    //유저가 삭제된 경우 로그아웃
                    notifyRefreshTokenExpired()
                    return null
                }
            }
        }

        //요청 헤더에 Bearer Token이 있는지 확인
        when (hasBearerToken(response)) {
            false -> {
                //없으면 아무것도 하지 않음
                return null
            }
            true -> {
                val previousRetryCount = retryCount(response)

                //있으면 Refresh Token을 사용하여 Access Token 갱신
                return reAuthenticate(
                    response.priorResponse?.request,
                    previousRetryCount + 1
                )
            }
        }
    }

    private fun retryCount(response: Response?): Int {
        return response?.request?.header("xObidanRetryCount")?.toInt() ?: 0
    }

    private fun hasBearerToken(response: Response?): Boolean {
        response?.let {
            val authorizationHeader = it.request.header("Authorization")
            return authorizationHeader!!.startsWith("Bearer")
        }
        return false
    }

    /**
     * 동시간대 여러 실패 건에 대해 동일한 Refresh Token을 사용하여 갱신하지 않도록 Synchronize 함수 정의
     */
    @Synchronized
    private fun reAuthenticate(request: Request?, retryCount: Int): Request? {

        if (retryCount > 1) {
            return null
        }

        val token = runBlocking {
            withContext(Dispatchers.Default)  {
                userApi.reissue(TokenRequest(Token.accessToken, Token.refreshToken))
                    .onErrorComplete {
                        //Refresh Token이 유효하지 않은 경우 로그아웃
                        when(it.toErrorResponseOrNull()!!.code) {
                            ErrorCode.REFRESH_TOKEN_ERROR.code, ErrorCode.REFRESH_TOKEN_NOT_FOUND.code, ErrorCode.USER_NOT_FOUND.code -> {
                                notifyRefreshTokenExpired()
                            }
                        }
                        true
                    }
                    .awaitSingleOrNull()
            }
        }

        return token?.let {
            Token.save(
                it.grantType,
                it.accessToken,
                it.refreshToken,
                it.accessTokenExpiresIn,
            )

            rewriteRequest(request, retryCount, it.accessToken)
        }
    }

    private fun rewriteRequest(
        staleRequest: Request?, retryCount: Int, authToken: String
    ): Request? {
        return staleRequest?.newBuilder()
            ?.header(
                "Authorization",
                "Bearer $authToken"
            )
            ?.header(
                "xObidanRetryCount",
                "$retryCount"
            )
            ?.build()
    }
}
