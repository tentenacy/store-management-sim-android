package com.tenutz.storemngsim.data.datasource.api.dto.terms

data class TermsDetailsResponse(
    val termCd: String?,
    val content: String?,
    val use: Boolean?,
    val createdAt: String?,
    val createdBy: String?,
    val openDate: String?,
    val closeDate: String?,
)