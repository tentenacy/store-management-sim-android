package com.tenutz.storemngsim.ui.menu.category.main

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tenutz.storemngsim.data.datasource.api.dto.category.MainCategoriesResponse
import com.tenutz.storemngsim.databinding.ItemMainCategoriesBinding
import com.tenutz.storemngsim.ui.base.BaseRecyclerView
import com.tenutz.storemngsim.ui.base.BaseViewHolder

class MainCategoriesViewHolder(val binding: ItemMainCategoriesBinding, private val listener: (MainCategoriesResponse.MainCategory) -> Unit): BaseViewHolder<MainCategoriesResponse.MainCategory>(binding.root) {

    override fun bind(item: MainCategoriesResponse.MainCategory) {
        binding.name = item.categoryName
        binding.code = item.categoryCode
        binding.use = item.use
        binding.constraintImainCategories.setOnClickListener {
            listener(item)
        }
    }
}

class MainCategoriesAdapter(private val listener: (MainCategoriesResponse.MainCategory) -> Unit): BaseRecyclerView<MainCategoriesResponse.MainCategory, MainCategoriesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainCategoriesViewHolder {
        return MainCategoriesViewHolder(ItemMainCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false), listener)
    }
}