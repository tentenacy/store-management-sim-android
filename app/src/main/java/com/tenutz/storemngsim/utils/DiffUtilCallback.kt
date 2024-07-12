package com.tenutz.storemngsim.utils

import androidx.recyclerview.widget.DiffUtil
import com.tenutz.storemngsim.data.datasource.api.dto.category.MainCategoriesResponse
import com.tenutz.storemngsim.data.datasource.api.dto.category.MiddleCategoriesResponse
import com.tenutz.storemngsim.ui.menu.category.main.args.MainCategoriesEditArgs
import com.tenutz.storemngsim.ui.menu.category.sub.SubCategoriesItem

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
            oldItem is SubCategoriesItem.Header && newItem is SubCategoriesItem.Header -> {
                true
            }
            oldItem is SubCategoriesItem.Data && newItem is SubCategoriesItem.Data -> {
                oldItem.value.categoryCode == newItem.value.categoryCode
            }
            else -> {
                false
            }
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}