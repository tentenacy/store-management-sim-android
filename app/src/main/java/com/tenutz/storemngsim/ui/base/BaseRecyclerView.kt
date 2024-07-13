package com.tenutz.storemngsim.ui.base

import android.view.MotionEvent
import android.view.View
import androidx.core.view.MotionEventCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tenutz.storemngsim.ui.menu.category.main.MainCategoriesEditAdapter
import com.tenutz.storemngsim.ui.menu.category.main.MainCategoriesEditViewHolder
import com.tenutz.storemngsim.ui.menu.category.main.MainCategoriesViewHolder
import com.tenutz.storemngsim.utils.DiffUtilCallback

abstract class BaseRecyclerView<T: Any, VH: BaseViewHolder<T>>: RecyclerView.Adapter<VH>() {

    val items = arrayListOf<T>()

    fun updateItems(items: List<T>?) {
        items?.let {
            val diffCallback = DiffUtilCallback(this.items, items)
            val diffResult = DiffUtil.calculateDiff(diffCallback)

            this.items.run {
                clear()
                addAll(items)
                diffResult.dispatchUpdatesTo(this@BaseRecyclerView)
            }
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        items.getOrNull(position)?.let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int = items.size
}