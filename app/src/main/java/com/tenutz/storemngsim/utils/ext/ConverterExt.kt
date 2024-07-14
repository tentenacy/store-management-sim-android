package com.tenutz.storemngsim.utils.ext

import android.content.res.Resources
import android.util.TypedValue
import com.google.gson.Gson
import com.tenutz.storemngsim.data.datasource.api.dto.common.ErrorResponse
import com.tenutz.storemngsim.data.datasource.api.dto.common.TokenResponse
import okhttp3.ResponseBody
import retrofit2.HttpException

fun ResponseBody.toErrorResponseOrNull(): ErrorResponse? {
    return try {
        Gson().fromJson(charStream(), ErrorResponse::class.java)
            .run { if (isNotEmpty()) this else null }
    } catch (e: Exception) {
        null
    }
}

fun ResponseBody.toTokenResponseOrNull(): TokenResponse? {
    return try {
        Gson().fromJson(charStream(), TokenResponse::class.java)
            .run { if (isNotEmpty()) this else null }
    } catch (e: Exception) {
        null
    }
}

fun Throwable.toErrorResponseOrNull(): ErrorResponse? {
    return try {
        if (this is HttpException) {
            this.response()?.errorBody()?.toErrorResponseOrNull()
        } else {
            null
        }
    } catch (e: Exception) {
        null
    }
}

val Number.toPx
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    )