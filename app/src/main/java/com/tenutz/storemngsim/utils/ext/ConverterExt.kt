package com.tenutz.storemngsim.utils

import com.google.gson.Gson
import com.tenutz.storemngsim.data.datasource.api.dto.common.ErrorResponse
import com.tenutz.storemngsim.data.datasource.api.dto.common.TokenResponse
import okhttp3.ResponseBody
import retrofit2.HttpException

fun ResponseBody.toErrorResponseOrNull(): ErrorResponse? {
    return try {
        Gson().fromJson(charStream(), ErrorResponse::class.java).run { if(isNotEmpty()) this else null }
    } catch (e: Exception) {
        null
    }
}

fun ResponseBody.toTokenResponseOrNull(): TokenResponse? {
    return try {
        Gson().fromJson(charStream(), TokenResponse::class.java).run { if(isNotEmpty()) this else null }
    } catch (e: Exception) {
        null
    }
}

fun Throwable.toErrorResponseOrNull(): ErrorResponse? {
    return try {
        if(this is HttpException) {
            this.response()?.errorBody()?.toErrorResponseOrNull()
        } else {
            null
        }
    } catch (e: Exception) {
        null
    }
}