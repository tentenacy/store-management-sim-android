package com.tenutz.storemngsim.data.datasource.api.dto.push

import com.google.gson.annotations.SerializedName
import java.util.*

data class PushAlarmsResponse(
    val seq: Int,
    val title: String?,
    val contents: String?,
    val image: String?,
    @SerializedName("createDate") val date: Date?,
)
