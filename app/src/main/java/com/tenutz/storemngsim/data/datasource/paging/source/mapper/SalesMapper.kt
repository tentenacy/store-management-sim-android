package com.tenutz.storemngsim.data.datasource.paging.source.mapper

import com.tenutz.storemngsim.data.datasource.api.dto.common.PageResponse
import com.tenutz.storemngsim.data.datasource.api.dto.store.StatisticsSalesByMenusResponse
import com.tenutz.storemngsim.data.datasource.paging.entity.MenuSalesList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SalesMapper @Inject constructor(

) {
    fun transform(response: PageResponse<StatisticsSalesByMenusResponse>): MenuSalesList {
        return with(response) {
            MenuSalesList(
                total = totalPages,
                page = number,
                menuSalesList = content.map {
                    MenuSalesList.MenuSales(
                        id = "${it.mainCategoryCode}${it.middleCategoryCode}${it.subCategoryCode}${it.menuName}".hashCode().toLong(),
                        soldAt = it.soldAt,
                        menuName = it.menuName,
                        categoryName = it.categoryName,
                        mainEquipmentName = it.mainEquipmentName,
                        middleEquipmentName = it.middleEquipmentName,
                        mainCategoryCode = it.mainCategoryCode,
                        middleCategoryCode = it.middleCategoryCode,
                        subCategoryCode = it.subCategoryCode,
                        cAuthAmount = it.cAuthAmount,
                        cAuthCount = it.cAuthCount,
                        authAmount = it.authAmount,
                        authCount = it.authCount,
                        authCAmount = it.authCAmount,
                        authCCount = it.authCCount,
                    )
                }
            )
        }
    }


}