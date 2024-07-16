package com.tenutz.storemngsim.data.repository.help

import com.tenutz.storemngsim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.storemngsim.data.datasource.api.dto.help.HelpsResponse
import io.reactivex.rxjava3.core.Single

interface HelpRepository {

    fun helps(commonCond: CommonCondition? = null): Single<Result<HelpsResponse>>
}