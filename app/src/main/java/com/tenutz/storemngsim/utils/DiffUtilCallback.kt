package com.tenutz.storemngsim.utils

import androidx.recyclerview.widget.DiffUtil
import com.tenutz.storemngsim.data.datasource.api.dto.category.MainCategoriesResponse
import com.tenutz.storemngsim.data.datasource.api.dto.category.MiddleCategoriesResponse
import com.tenutz.storemngsim.data.datasource.api.dto.menu.MainMenuMappersResponse
import com.tenutz.storemngsim.data.datasource.api.dto.menu.MainMenuOptionGroupsResponse
import com.tenutz.storemngsim.data.datasource.api.dto.menu.MainMenusResponse
import com.tenutz.storemngsim.data.datasource.api.dto.option.OptionMappersResponse
import com.tenutz.storemngsim.data.datasource.api.dto.option.OptionOptionGroupsResponse
import com.tenutz.storemngsim.data.datasource.api.dto.option.OptionsResponse
import com.tenutz.storemngsim.data.datasource.api.dto.optiongroup.*
import com.tenutz.storemngsim.data.datasource.api.dto.store.StatisticsSalesByCreditCardResponse
import com.tenutz.storemngsim.ui.help.args.HelpsArgs
import com.tenutz.storemngsim.ui.menu.category.main.args.MainCategoriesEditArgs
import com.tenutz.storemngsim.ui.menu.category.middle.args.MiddleCategoriesEditArgs
import com.tenutz.storemngsim.ui.menu.category.sub.SubCategoriesEditItem
import com.tenutz.storemngsim.ui.menu.category.sub.SubCategoriesItem
import com.tenutz.storemngsim.ui.menu.mainmenu.args.MainMenusEditArgs
import com.tenutz.storemngsim.ui.menu.mainmenu.optiongroup.args.MmOptionGroupsEditArgs
import com.tenutz.storemngsim.ui.menu.optiongroup.args.OptionGroupsEditArgs
import com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.mainmenu.args.OgMainMenuAddArgs
import com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.mainmenu.args.OgMainMenusEditArgs
import com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.mainmenu.args.OgOptionMenuAddArgs
import com.tenutz.storemngsim.ui.menu.optiongroup.mappingmenu.mainmenu.args.OgOptionMenusEditArgs
import com.tenutz.storemngsim.ui.menu.optionmenu.args.OptionMenusEditArgs
import com.tenutz.storemngsim.ui.menu.optionmenu.optiongroup.args.OmOptionGroupsEditArgs

class DiffUtilCallback<out T: Any>(
    private val oldList: List<T>,
    private val newList: List<T>,
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return when {
            oldItem is MainCategoriesResponse.MainCategory && newItem is MainCategoriesResponse.MainCategory -> {
                (oldItem.categoryCode == newItem.categoryCode)
            }
            oldItem is MainCategoriesEditArgs.MainCategoryEdit && newItem is MainCategoriesEditArgs.MainCategoryEdit -> {
                (oldItem.categoryCode == newItem.categoryCode) &&
                (oldItem.checked == newItem.checked)
            }
            oldItem is MiddleCategoriesResponse.MiddleCategory && newItem is MiddleCategoriesResponse.MiddleCategory -> {
                (oldItem.categoryCode == newItem.categoryCode)
            }
            oldItem is MiddleCategoriesEditArgs.MiddleCategoryEdit && newItem is MiddleCategoriesEditArgs.MiddleCategoryEdit -> {
                (oldItem.categoryCode == newItem.categoryCode) &&
                (oldItem.checked == newItem.checked)
            }
            oldItem is SubCategoriesItem.Header && newItem is SubCategoriesItem.Header -> {
                true
            }
            oldItem is SubCategoriesItem.Data && newItem is SubCategoriesItem.Data -> {
                oldItem.value.categoryCode == newItem.value.categoryCode
            }
            oldItem is SubCategoriesEditItem.Header && newItem is SubCategoriesEditItem.Header -> {
                true
            }
            oldItem is SubCategoriesEditItem.Data && newItem is SubCategoriesEditItem.Data -> {
                (oldItem.value.categoryCode == newItem.value.categoryCode) &&
                (oldItem.value.checked == newItem.value.checked)
            }
            oldItem is MainMenusResponse.MainMenu && newItem is MainMenusResponse.MainMenu -> {
                oldItem.menuCode == newItem.menuCode
            }
            oldItem is MainMenusEditArgs.MainMenuEdit && newItem is MainMenusEditArgs.MainMenuEdit -> {
                (oldItem.menuCode == newItem.menuCode) &&
                (oldItem.checked == newItem.checked)
            }
            oldItem is MainMenuMappersResponse.MainMenuMapper && newItem is MainMenuMappersResponse.MainMenuMapper -> {
                oldItem.optionGroupCode == newItem.optionGroupCode
            }
            oldItem is MainMenuOptionGroupsResponse.MainMenuOptionGroup && newItem is MainMenuOptionGroupsResponse.MainMenuOptionGroup -> {
                oldItem.optionGroupCode == newItem.optionGroupCode
            }
            oldItem is MmOptionGroupsEditArgs.MainMenuOptionGroup && newItem is MmOptionGroupsEditArgs.MainMenuOptionGroup -> {
                oldItem.optionGroupCode == newItem.optionGroupCode
            }
            oldItem is OptionsResponse.Option && newItem is OptionsResponse.Option -> {
                oldItem.optionCode == newItem.optionCode
            }
            oldItem is OptionMenusEditArgs.OptionEdit && newItem is OptionMenusEditArgs.OptionEdit -> {
                (oldItem.optionCode == newItem.optionCode) &&
                        (oldItem.checked == newItem.checked)
            }
            oldItem is OptionMappersResponse.OptionMapper && newItem is OptionMappersResponse.OptionMapper -> {
                oldItem.optionGroupCode == newItem.optionGroupCode
            }
            oldItem is OptionOptionGroupsResponse.OptionOptionGroup && newItem is OptionOptionGroupsResponse.OptionOptionGroup -> {
                oldItem.optionGroupCode == newItem.optionGroupCode
            }
            oldItem is OmOptionGroupsEditArgs.OptionOptionGroupEdit && newItem is OmOptionGroupsEditArgs.OptionOptionGroupEdit -> {
                oldItem.optionGroupCode == newItem.optionGroupCode
            }
            oldItem is OptionGroupsResponse.OptionGroup && newItem is OptionGroupsResponse.OptionGroup -> {
                oldItem.optionGroupCode == newItem.optionGroupCode
            }
            oldItem is OptionGroupsEditArgs.OptionGroup && newItem is OptionGroupsEditArgs.OptionGroup -> {
                (oldItem.optionGroupCode == newItem.optionGroupCode) &&
                        (oldItem.checked == newItem.checked)
            }
            oldItem is OptionGroupMainMenuMappersResponse.OptionGroupMainMenuMapper && newItem is OptionGroupMainMenuMappersResponse.OptionGroupMainMenuMapper -> {
                oldItem.mainCategoryCode == newItem.mainCategoryCode &&
                oldItem.middleCategoryCode == newItem.middleCategoryCode &&
                oldItem.subCategoryCode == newItem.subCategoryCode &&
                oldItem.menuCode == newItem.menuCode
            }
            oldItem is OgMainMenusEditArgs.OptionGroupMainMenuMapper && newItem is OgMainMenusEditArgs.OptionGroupMainMenuMapper -> {
                oldItem.mainCategoryCode == newItem.mainCategoryCode &&
                        oldItem.middleCategoryCode == newItem.middleCategoryCode &&
                        oldItem.subCategoryCode == newItem.subCategoryCode &&
                        oldItem.menuCode == newItem.menuCode &&
                        oldItem.checked == newItem.checked
            }
            oldItem is OptionGroupMainMenusResponse.OptionGroupMainMenu && newItem is OptionGroupMainMenusResponse.OptionGroupMainMenu -> {
                oldItem.mainCategoryCode == newItem.mainCategoryCode &&
                oldItem.middleCategoryCode == newItem.middleCategoryCode &&
                oldItem.subCategoryCode == newItem.subCategoryCode &&
                oldItem.menuCode == newItem.menuCode
            }
            oldItem is OgMainMenuAddArgs.OptionGroupMainMenus && newItem is OgMainMenuAddArgs.OptionGroupMainMenus -> {
                oldItem.mainCategoryCode == newItem.mainCategoryCode &&
                        oldItem.middleCategoryCode == newItem.middleCategoryCode &&
                        oldItem.subCategoryCode == newItem.subCategoryCode &&
                        oldItem.menuCode == newItem.menuCode &&
                        oldItem.expanded == newItem.expanded
            }

            oldItem is OptionGroupOptionMappersResponse.OptionGroupOptionMapper && newItem is OptionGroupOptionMappersResponse.OptionGroupOptionMapper -> {
                oldItem.optionCode == newItem.optionCode
            }
            oldItem is OgOptionMenusEditArgs.OptionGroupOptionMenuMapper && newItem is OgOptionMenusEditArgs.OptionGroupOptionMenuMapper -> {
                oldItem.optionCode == newItem.optionCode &&
                        oldItem.checked == newItem.checked
            }
            oldItem is OptionGroupOptionsResponse.OptionGroupOption && newItem is OptionGroupOptionsResponse.OptionGroupOption -> {
                oldItem.optionCode == newItem.optionCode
            }
            oldItem is OgOptionMenuAddArgs.OptionGroupOptionMenus && newItem is OgOptionMenuAddArgs.OptionGroupOptionMenus -> {
                oldItem.optionCode == newItem.optionCode
            }
            oldItem is HelpsArgs.Help && newItem is HelpsArgs.Help -> {
                oldItem.seq == newItem.seq &&
                oldItem.expanded == newItem.expanded
            }
            oldItem is StatisticsSalesByCreditCardResponse.StatisticsSalesByCreditCard && newItem is StatisticsSalesByCreditCardResponse.StatisticsSalesByCreditCard -> {
                oldItem.date == newItem.date &&
                oldItem.creditCardCompany == newItem.creditCardCompany
            }
            else -> {
                false
            }
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}