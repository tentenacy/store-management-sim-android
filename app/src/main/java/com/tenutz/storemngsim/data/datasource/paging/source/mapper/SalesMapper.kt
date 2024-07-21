package com.tenutz.storemngsim.data.datasource.paging.source.mapper

import com.tenutz.storemngsim.data.datasource.api.dto.common.PageResponse
import com.tenutz.storemngsim.data.datasource.api.dto.store.SalesResponse
import com.tenutz.storemngsim.data.datasource.api.dto.store.StatisticsSalesByMenusResponse
import com.tenutz.storemngsim.data.datasource.paging.entity.MenuSalesList
import com.tenutz.storemngsim.data.datasource.paging.entity.SalesList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SalesMapper @Inject constructor(

) {

    fun transform(response: PageResponse<SalesResponse>): SalesList {
        return with(response) {
            SalesList(
                total = totalPages,
                page = number,
                salesList = content.map {
                    SalesList.Sales(
                        "${it.storeCode}${it.orderNumber}".hashCode().toLong(),
                        storeCode = it.storeCode,
                        orderNumber = it.orderNumber,
                        orderType = it.orderType,
                        orderedAt = it.orderedAt,
                        posNumber = it.posNumber,
                        approvalType = it.approvalType,
                        paymentType = it.paymentType,
                        paymentAmount = it.paymentAmount,
                        saleNumber = it.saleNumber,
                        approvalNumber = it.approvalNumber,
                        creditCardCompanyName = it.creditCardCompanyName,
                    )
                }
            )
        }
    }

    fun transform(response: PageResponse<StatisticsSalesByMenusResponse>): MenuSalesList {
        return with(response) {
            MenuSalesList(
                total = totalPages,
                page = number,
                menuSalesList = content.map {
                    MenuSalesList.MenuSales(
                        "${it.mainCategoryCode}${it.middleCategoryCode}${it.subCategoryCode}${it.menuCode}".hashCode().toLong(),
                        it.soldAt,
                        it.menuName,
                        it.categoryName,
                        it.mainEquipmentName,
                        it.middleEquipmentName,
                        it.mainCategoryCode,
                        it.middleCategoryCode,
                        it.subCategoryCode,
                        it.menuCode,
                        it.cAuthAmount,
                        it.cAuthCount,
                        it.authAmount,
                        it.authCount,
                        it.authCAmount,
                        it.authCCount,
                    )
                }
            )
        }
    }
}