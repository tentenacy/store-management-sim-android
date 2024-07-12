package com.tenutz.storemngsim.data.datasource.api.dto.menu

data class MenuPrioritiesChangeRequest(
    val menus: List<MainCategory>,
) {
    data class MainCategory(
        val menuCode: String,
        val priority: Int,
    )
}
