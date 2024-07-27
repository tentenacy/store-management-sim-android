package com.tenutz.storemngsim.di.qualifier

enum class NavigationGraphs(val navigationGraphName: String) {
    MAIN("MainNavigation"),
    MAIN_CATEGORY("MainCategoryNavigation"),
    MAIN_MENU("MainMenuNavigation"),
    MM_OPTION_GROUP("MmOptionGroupNavigation"),
    OG_MAPPING_MENU("OgMappingMenuNavigation"),
    OM_OPTION_GROUP("OmOptionGroupNavigation"),
    OPTION_GROUP("OptionGroupNavigation"),
    OPTION_MENU("OptionMenuNavigation"),
    REVIEW("ReviewNavigation"),
    STATISTICS("StatisticsNavigation"),
    SUB_CATEGORY("SubCategoryNavigation"),
}