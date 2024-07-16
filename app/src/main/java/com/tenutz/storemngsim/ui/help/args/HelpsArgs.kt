package com.tenutz.storemngsim.ui.help.args

data class HelpsArgs(
    val helps: List<Help>
) {
    data class Help(
        val seq: Int?,
        val title: String?,
        val content: String?,
        val imageName: String?,
        val imageUrl: String?,
        val createdAt: String?,
        val createdBy: String?,
        val createdIp: String?,
        val updatedAt: String?,
        val updatedBy: String?,
        val updatedIp: String?,
        var expanded: Boolean = false,
    )
}
