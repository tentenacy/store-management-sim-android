package com.tenutz.storemngsim.ui.menu.optionmenu.optiongroup

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tenutz.storemngsim.data.datasource.api.dto.category.MiddleCategoriesResponse
import com.tenutz.storemngsim.data.datasource.api.dto.menu.MainMenuOptionGroupsResponse
import com.tenutz.storemngsim.data.datasource.api.dto.option.OptionOptionGroupsResponse
import com.tenutz.storemngsim.databinding.ItemOmOptionGroupAddBinding
import com.tenutz.storemngsim.ui.base.BaseRecyclerView
import com.tenutz.storemngsim.ui.base.BaseViewHolder

class OmOptionGroupAddViewHolder(val binding: ItemOmOptionGroupAddBinding, private val listener: (OptionOptionGroupsResponse.OptionOptionGroup) -> Unit): BaseViewHolder<OptionOptionGroupsResponse.OptionOptionGroup>(binding.root) {

    override fun bind(item: OptionOptionGroupsResponse.OptionOptionGroup) {
        binding.name = item.optionName
        binding.code = item.optionGroupCode
        binding.textIomOptionGroupAdd.setOnClickListener {
            listener(item)
        }
    }
}

class OmOptionGroupAddAdapter(private val listener: (OptionOptionGroupsResponse.OptionOptionGroup) -> Unit): BaseRecyclerView<OptionOptionGroupsResponse.OptionOptionGroup, OmOptionGroupAddViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OmOptionGroupAddViewHolder {
        return OmOptionGroupAddViewHolder(ItemOmOptionGroupAddBinding.inflate(LayoutInflater.from(parent.context), parent, false), listener)
    }
}