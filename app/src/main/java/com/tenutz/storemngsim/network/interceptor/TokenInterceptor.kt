package com.tenutz.storemngsim.network.interceptor

import com.tenutz.storemngsim.data.datasource.sharedpref.Token
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(
                "Authorization",
                if (Token.accessToken.isNotBlank()) "Bearer ${Token.accessToken}" else ""
            )
            .build()

        val proceed = chain.proceed(request)
        return proceed
    }
}