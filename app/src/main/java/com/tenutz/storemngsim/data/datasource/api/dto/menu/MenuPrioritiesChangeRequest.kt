package com.tenutz.storemngsim.data.datasource.api.dto.menu

data class MenuPrioritiesChangeRequest(
    val menus: List<MainMenu>,
) {
    data class MainMenu(
        val menuCode: String,
        val priority: Int,
    )
}
