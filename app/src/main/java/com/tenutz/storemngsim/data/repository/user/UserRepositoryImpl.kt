package com.tenutz.storemngsim.data.repository.user

import com.tenutz.storemngsim.data.datasource.api.SCKApi
import com.tenutz.storemngsim.data.datasource.api.dto.common.TokenRequest
import com.tenutz.storemngsim.data.datasource.api.dto.common.TokenResponse
import com.tenutz.storemngsim.data.datasource.api.dto.user.SocialLoginRequest
import com.tenutz.storemngsim.data.datasource.api.dto.user.SocialSignupRequest
import com.tenutz.storemngsim.data.datasource.api.dto.user.UserDetailsResponse
import com.tenutz.storemngsim.data.datasource.sharedpref.OAuthToken
import com.tenutz.storemngsim.utils.type.SocialType
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val sckApi: SCKApi,
) : UserRepository {
    override fun socialSignup(socialType: SocialType, request: SocialSignupRequest): Single<Unit> =
        sckApi.socialSignup(
            socialType.name,
            request,
        )

    override fun socialLogin(
        socialType: SocialType,
    ): Single<TokenResponse> =
        sckApi.socialLogin(
            socialType.name,
            SocialLoginRequest(OAuthToken.accessToken),
        )

    override fun reissue(request: TokenRequest): Single<TokenResponse> =
        sckApi.reissue(request)

    override fun userDetails(): Single<UserDetailsResponse> =
        sckApi.userDetails()
}