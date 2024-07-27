package com.tenutz.storemngsim.ui.menu.category.middle

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tenutz.storemngsim.data.datasource.api.dto.category.MiddleCategoriesResponse
import com.tenutz.storemngsim.databinding.ItemMiddleCategoriesBinding
import com.tenutz.storemngsim.ui.base.BaseRecyclerView
import com.tenutz.storemngsim.ui.base.BaseViewHolder

class MiddleCategoriesViewHolder(val binding: ItemMiddleCategoriesBinding, private val listener: (MiddleCategoriesResponse.MiddleCategory) -> Unit): BaseViewHolder<MiddleCategoriesResponse.MiddleCategory>(binding.root) {

    override fun bind(position: Int, item: MiddleCategoriesResponse.MiddleCategory) {
        binding.name = item.categoryName
        binding.code = item.categoryCode
        binding.use = item.use
        binding.constraintImiddleCategories.setOnClickListener {
            listener(item)
        }
    }
}

class MiddleCategoriesAdapter(private val listener: (MiddleCategoriesResponse.MiddleCategory) -> Unit): BaseRecyclerView<MiddleCategoriesResponse.MiddleCategory, MiddleCategoriesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiddleCategoriesViewHolder {
        return MiddleCategoriesViewHolder(ItemMiddleCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false), listener)
    }
}