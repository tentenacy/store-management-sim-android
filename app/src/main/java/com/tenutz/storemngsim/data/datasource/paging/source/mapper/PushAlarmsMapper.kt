package com.tenutz.storemngsim.data.datasource.paging.source.mapper

import com.tenutz.storemngsim.data.datasource.api.dto.common.PageResponse
import com.tenutz.storemngsim.data.datasource.api.dto.push.PushAlarmsResponse
import com.tenutz.storemngsim.data.datasource.paging.entity.PushAlarms
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PushAlarmsMapper @Inject constructor(

) {

    fun transform(response: PageResponse<PushAlarmsResponse>): PushAlarms {
        return with(response) {
            PushAlarms(
                total = totalPages,
                page = number,
                pushAlarms = content.map {
                    PushAlarms.PushAlarm(
                        it.seq.toLong(),
                        title = it.title,
                        contents = it.contents,
                        image = it.image,
                        date = it.date,
                    )
                }
            )
        }
    }
}