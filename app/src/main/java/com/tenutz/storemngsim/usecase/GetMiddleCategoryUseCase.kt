package com.tenutz.storemngsim.usecase

import com.tenutz.storemngsim.data.repository.category.CategoryRepository

class GetMiddleCategoryUseCase(private val categoryRepository: CategoryRepository) {
    operator fun invoke(mainCategoryCode: String = "2000", middleCategoryCode: String = "3000") = categoryRepository.middleCategory(mainCategoryCode, middleCategoryCode)
}