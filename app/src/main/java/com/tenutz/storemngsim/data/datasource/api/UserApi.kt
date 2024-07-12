package com.tenutz.storemngsim.data.datasource.api

import com.tenutz.storemngsim.data.datasource.api.dto.common.TokenRequest
import com.tenutz.storemngsim.data.datasource.api.dto.common.TokenResponse
import com.tenutz.storemngsim.data.datasource.api.dto.user.SocialLoginRequest
import com.tenutz.storemngsim.data.datasource.api.dto.user.SocialSignupRequest
import com.tenutz.storemngsim.data.datasource.api.dto.user.UserDetailsResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApi {

    @POST("/users/social/{socialType}")
    fun socialSignup(
        @Path("socialType") socialType: String,
        @Body request: SocialSignupRequest
    ): Single<Unit>

    @POST("/users/social/{socialType}/token")
    fun socialLogin(
        @Path("socialType") socialType: String,
        @Body request: SocialLoginRequest
    ): Single<TokenResponse>

    @POST("/users/token/expiration")
    fun reissue(@Body request: TokenRequest): Single<TokenResponse>

    @GET("/users/details")
    fun userDetails(): Single<UserDetailsResponse>
}