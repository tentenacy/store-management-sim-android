package com.tenutz.storemngsim.data.datasource.paging.source.mapper

import com.tenutz.storemngsim.data.datasource.api.dto.common.PageResponse
import com.tenutz.storemngsim.data.datasource.api.dto.store.MenuReviewsResponse
import com.tenutz.storemngsim.data.datasource.api.dto.store.StoreReviewsResponse
import com.tenutz.storemngsim.data.datasource.paging.entity.MenuReviews
import com.tenutz.storemngsim.data.datasource.paging.entity.StoreReviews
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReviewsMapper @Inject constructor(

) {
    fun transform(response: PageResponse<StoreReviewsResponse>): StoreReviews {
        return with(response) {
            StoreReviews(
                total = totalPages,
                page = number,
                storeReviews = content.map {
                    StoreReviews.StoreReview(
                        seq = it.seq,
                        siteCode = it.siteCode,
                        storeCode = it.storeCode,
                        middleCategoryCode = it.middleCategoryCode,
                        middleCategoryName = it.middleCategoryName,
                        createdBy = it.createdBy,
                        createdAt = it.createdAt,
                        content = it.content,
                        keyword = it.keyword,
                        level = it.level,
                        rating = it.rating,
                        sno = it.sno,
                        storeReviewReply = it.storeReviewReply?.let {
                            StoreReviews.StoreReview.StoreReviewReply(
                                seq = it.seq,
                                siteCode = it.siteCode,
                                storeCode = it.storeCode,
                                middleCategoryCode = it.middleCategoryCode,
                                createdBy = it.createdBy,
                                createdAt = it.createdAt,
                                content = it.content,
                            )
                        }
                    )
                }
            )
        }
    }

    fun transform(response: PageResponse<MenuReviewsResponse>): MenuReviews {
        return with(response) {
            MenuReviews(
                total = totalPages,
                page = number,
                menuReviews = content.map {
                    MenuReviews.MenuReview(
                        seq = it.seq,
                        siteCode = it.siteCode,
                        storeCode = it.storeCode,
                        mainCategoryCode = it.mainCategoryCode,
                        middleCategoryCode = it.middleCategoryCode,
                        subCategoryCode = it.subCategoryCode,
                        menuCode = it.menuCode,
                        menuName = it.menuName,
                        imageUrl = it.imageUrl,
                        createdBy = it.createdBy,
                        createdAt = it.createdAt,
                        content = it.content,
                        keyword = it.keyword,
                        level = it.level,
                        rating = it.rating,
                        sno = it.sno,
                        menuReviewReply = it.menuReviewReply?.let { menuReply ->
                            MenuReviews.MenuReview.MenuReviewReply(
                                seq = menuReply.seq,
                                siteCode = menuReply.siteCode,
                                storeCode = menuReply.storeCode,
                                mainCategoryCode = menuReply.mainCategoryCode,
                                middleCategoryCode = menuReply.middleCategoryCode,
                                subCategoryCode = menuReply.subCategoryCode,
                                menuCode = menuReply.menuCode,
                                createdBy = menuReply.createdBy,
                                createdAt = menuReply.createdAt,
                                content = menuReply.content,
                            )
                        }
                    )
                }
            )
        }
    }
}