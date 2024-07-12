package com.tenutz.storemngsim.utils

import com.tenutz.storemngsim.ui.menu.category.main.MainCategoriesEditViewHolder

interface OnDragListener<VH> {
    fun onDragStart(holder: VH)
    fun onDragOver()
}