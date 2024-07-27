package com.tenutz.storemngsim.data.datasource.paging.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class PushAlarms(
    val total: Int = 0,
    val page: Int = 0,
    val pushAlarms: List<PushAlarm>
): Parcelable {

    @IgnoredOnParcel
    val endOfPage = total - 1 <= page

    @Parcelize
    @Entity(tableName = "push_alarm")
    data class PushAlarm(
        @PrimaryKey val seq: Long,
        val title: String?,
        val contents: String?,
        val image: String?,
        val date: Date?,
    ): Parcelable

    @Parcelize
    @Entity(tableName = "push_alarm_remote_keys")
    data class PushAlarmRemoteKeys(
        @PrimaryKey(autoGenerate = true) val id: Long,
        val prevKey: Int?,
        val nextKey: Int,
    ): Parcelable
}