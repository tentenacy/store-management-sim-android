package com.tenutz.storemngsim.data.datasource.api.dto.terms

data class TermsResponse(
    val terms: List<Term>
) {

    data class Term(
        val termCd: String?,
        val content: String?,
        val use: Boolean?,
        val createdAt: String?,
        val createdBy: String?,
        val openDate: String?,
        val closeDate: String?,
    )
}